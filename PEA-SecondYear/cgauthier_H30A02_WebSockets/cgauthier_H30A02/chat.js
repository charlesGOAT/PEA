const app = require("express")();
const http = require("http").Server(app);
const e = require("cors");
const { NOTFOUND } = require("dns");
const fs = require("fs");
const fsPromises = fs.promises;
const path = require("path");
const io = require("socket.io")(http);

const PORT = 4513;
const WEBROOT = "public";

app.get("/", (res, resp) => {
  resp.sendFile(path.join(__dirname, WEBROOT, "/chat.html"));
});

app.get("/*", (req, resp) => {
  if (path.parse(req.url).base !== "favicon.ico")
    resp.sendFile(
      path.join(
        __dirname,
        WEBROOT,
        path.parse(req.url).dir,
        path.parse(req.url).base
      )
    );
});

//make people reveal eachother
let allUsers = [];
let privateRoomArray = [];
let userArray = [];
let userNames = [];
let colorArray = [];
let inviteArray = [];
let gameArray = [];
let date;

io.on("connection", (socket) => {
  date = new Date().toLocaleDateString();

  userArray.push(socket.id);

  let addr = socket.handshake;

  console.log(`Login from ${addr.url}`);

  socket.on("login", (user) => {
    //will have to make sure they are not case sensitive
    //color will be border color
    
    if (
      userNames.includes(user) ||
      user == "" ||
      user == "play" ||
      user == "accept" ||
      user.includes(" ")
    ) {
      socket.emit("invalidUser");
    } else {
      socket.emit("validUser");
      
      let newUser = new User(socket, user, null, null, null, null, null, null);
      newUser.createAvatar();
      newUser.newColor();
      //change the form to become the chat room
      
      allUsers.push(newUser);
      userNames.push(user);

      findUserColor(socket.id);
      
      socket.emit(
        "hi",
        new Message(
          userNames[userArray.indexOf(socket.id)],
          `Yo ${newUser.username}`,
          findUserColor(socket.id),
          newUser.avatar
        )
      );
    }
    let userSorted = userNames.map((a) => a); //creating deep copy
    userSorted.sort((a, b) => {
      return a.localeCompare(b);
    });

    io.emit("showUsers", userSorted);
  });
 

  socket.on("disconnect", () => {
    privateRoomArray.map((a) => {
      if (a.users.includes(findUser(userNames[userArray.indexOf(socket.id)]))) {
        a.removeUser(findUser(userNames[userArray.indexOf(socket.id)]));
      
        if (a.checkRoomEmpty()) {
          io.to(a.users[0].socket.id).emit("leaveRoom");
          a.users[0].socket.leave(
            `room${findPrivateRoom(a.users[0].socket.id).roomNumber}`
          );
          privateRoomArray.splice(privateRoomArray.indexOf(a), 1);
        }
      }
    });
    
    if (
      findGame(findUser(userNames[userArray.indexOf(socket.id)])) != undefined
    ) {
      findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0]
          .selection=null;
          findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[1]
          .selection=null;
      io.to(
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0]
          .socket.id
      ).emit("gameDone", null);
      io.to(
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[1]
          .socket.id
      ).emit("gameDone", null);
      gameArray.splice(
        gameArray.indexOf(
          findGame(findUser(userNames[userArray.indexOf(socket.id)]))
        ),
        1
      );
    }
    inviteArray.filter(
      (a) =>
        a.initiator.id != socket.id &&
        !a.peopleInvited.includes(userNames[userArray.indexOf(socket.id)])
    );
    allUsers.splice(userArray.indexOf(socket.id),1);
    userNames.splice(userArray.indexOf(socket.id), 1);
    
    userArray.splice(userArray.indexOf(socket.id), 1);
    colorArray.splice(userArray.indexOf(socket.id), 1);

    let userSorted = userNames.map((a) => a); //creating deep copy
    userSorted.sort((a, b) => {
      return a.localeCompare(b);
    });

    io.emit("showUsers", userSorted);
    //will have to check this for the leave
  });
  //have to fix because it appears on wrong side
  socket.on("privateMsg", (msg) => {

    let myUser = msg.substring(1, msg.indexOf(" "));

    let theMsg = msg.substring(msg.indexOf(" ")); //check this tmr

    if (userNames.includes(myUser)) {
      let theSocket = userArray[userNames.indexOf(myUser)];

      date = new Date();
      let log = `Private Message:${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()} ${date.getHours()}H: ${date.getMinutes()}M: ${date.getSeconds()}S user:${
        userNames[userArray.indexOf(socket.id)]
      } private message to: ${myUser} length: ${msg.length}\n`;
      writeFile(
        `./public/logs/${date.getFullYear()}-${date.getMonth()+1}-${date.getDate()}events.log`,
        log
      );

      io.to(theSocket).emit(
        "inviteAndPriv", //currently testing with the invite thing
        new Message(
          userNames[userArray.indexOf(socket.id)],
          theMsg,
          findUserColor(socket.id),
          findUser(userNames[userArray.indexOf(socket.id)]).avatar
        )
      );
    } else {
      socket.emit(
        "inviteAndPriv",
        new Message(
          userNames[userArray.indexOf(socket.id)],
          "the person you are trying to reach does not exist",
          findUserColor(socket.id),
          findUser(userNames[userArray.indexOf(socket.id)]).avatar
        )
      );
    }
  });

  socket.on("inviteUser", (msg) => {
    let myUser = msg.split(" ")[1];
    let inviteType = msg.split(" ")[0];

    let type;
    inviteType == "!invite" ? (type = "room") : (type = "game");

    let findInvitationSame = inviteArray.find(
      (a) =>
        a.initiator.id == socket.id &&
        a.type == type &&
        a.peopleInvited.includes(myUser)
    );

    if (findUser(myUser) === undefined) {
      socket.emit(
        "inviteAndPriv",
        new Message(
          "System",
          "the user you are trying to invite does not exist",
          findUserColor(socket.id),
          null
        )
      );
    } else if (findInvitationSame != undefined) {
      socket.emit(
        "inviteAndPriv",
        new Message(
          "System",
          "you cannot invite the same person for the same thing two times",
          findUserColor(socket.id),
          null
        )
      );
    } else if (myUser === userNames[userArray.indexOf(socket.id)]) {
      socket.emit(
        "inviteAndPriv",
        new Message(
          "System",
          "you cannot invite yourself to a game or room",
          findUserColor(socket.id),
          null
        )
      );
    } else if (inviteType == "!invite") {
      let theMsg = `${
        userNames[userArray.indexOf(socket.id)]
      } has sent you an invite in a private room`;

      let findInvitation = inviteArray.find(
        (a) => a.initiator.id == socket.id && a.type == "invite"
      );
      if (findInvitation == undefined)
        inviteArray.push(new Invitation(socket, [myUser], "room", "pending"));
      else {
        inviteArray = inviteArray.map((a) => {
          if (a == findInvitation) {
            a.peopleInvited.push(myUser);
          }
        });
      }

      let theSocket = userArray[userNames.indexOf(myUser)];

      io.to(theSocket).emit(
        "inviteAndPriv",
        new Message(
          userNames[userArray.indexOf(socket.id)],
          theMsg,
          findUserColor(socket.id),
          findUser(userNames[userArray.indexOf(socket.id)]).avatar
        )
      );
    } else {
      let invitation = new Invitation(socket, [myUser], "game", "pending");
      inviteArray.push(invitation); 

      io.to(userArray[userNames.indexOf(myUser)]).emit(
        "inviteAndPriv",
        new Message(
          "System",
          `You just got invited to play a game of Rock Paper Scissors by ${
            userNames[userArray.indexOf(socket.id)]
          }`,
          findUserColor(socket.id),
          findUser(userNames[userArray.indexOf(socket.id)]).avatar
        )
      );
    }
  });

  socket.on("privateRoomMsg", (msg) => {
    socket
      .to(`room${findPrivateRoom(socket.id).roomNumber}`)
      .emit(
        "privChat",
        new Message(
          userNames[userArray.indexOf(socket.id)],
          msg,
          findUserColor(socket.id),
          findUser(userNames[userArray.indexOf(socket.id)]).avatar
        )
      );
  });

  socket.on("globalInvite", () => {
    //Configure with the request class

    let msg = `${
      userNames[userArray.indexOf(socket.id)]
    } invites for a game of rock paper scissors.`;

    let playersExcept = userNames.filter(
      (a) => a != userNames[userArray.indexOf(socket.id)]
    );
    inviteArray.push(new Invitation(socket, playersExcept, "game", null));

    socket.broadcast.emit(
      "inviteAndPriv",
      new Message(
        userNames[userArray.indexOf(socket.id)],
        msg,
        findUserColor(socket.id),
        findUser(userNames[userArray.indexOf(socket.id)]).avatar
      )
    );
  });

  socket.on("cancelRequests", () => {
    let msg = `${
      userNames[userArray.indexOf(socket.id)]
    }'s requests have been canceled.'`;

    inviteArray = inviteArray.filter((a) => a.initiator.id != socket.id);

    io.emit(
      "inviteAndPriv",
      new Message(
        userNames[userArray.indexOf(socket.id)],
        msg,
        findUserColor(socket.id),
        findUser(userNames[userArray.indexOf(socket.id)]).avatar
      )
    );
  });

  socket.on("acceptRequest", (request) => {
    if (inviteArray.length > 0) {
      //important, pending invite removed
      if (request.type === null) {
        socket.emit(
          "inviteAndPriv",
          new Message(
            userNames[userArray.indexOf(socket.id)],
            "You must put a type of request to accept (Game or Room) and a user to come with",
            findUserColor(socket.id),
            findUser(userNames[userArray.indexOf(socket.id)]).avatar
          )
        );
      } else if (correspondingInvite(request, socket) === undefined) {
        socket.emit(
          "inviteAndPriv",
          new Message(
            userNames[userArray.indexOf(socket.id)],
            "There are no requests pending for you"
          )
        );
      } else {
        if (request.type == "game") {
          let msg1 = `You have accepted the game request to play against ${request.user}`;

          let msg2 = `${
            userNames[userArray.indexOf(socket.id)]
          } has accepted playing against you`;

          findInviteDelete(
            findUser(request.user),
            "game"
          );
          let player1 = findUser(
            request.user
          );

          let player2 = findUser(userNames[userArray.indexOf(socket.id)]);

          let playerArray = [player1, player2];

          gameArray.push(new Game(0, playerArray, 0));

          socket.emit(
            "gamePlayed",
            new Message(
              userNames[userArray.indexOf(socket.id)],
              msg1,
              findUserColor(socket.id),
              findUser(userNames[userArray.indexOf(socket.id)]).avatar
            )
          );
          io.to(userArray[userNames.indexOf(request.user)]).emit(
            "gamePlayed",
            new Message(
              userNames[userArray.indexOf(socket.id)],
              msg2,
              findUserColor(userArray[userNames.indexOf(request.user)])
            ),
            findUser(userNames[userArray.indexOf(socket.id)]).avatar
          );
        } //make this a bit more clear!!
        else {
          //else if instead of else cuz need to confirm game and player and need to check if I want to invite more users. When there is only one player, the room is deleted.
          let thePrivateRoom = findPrivateRoom(
            userArray[userNames.indexOf(request.user)]
          );
          findInviteDelete(
            findUser(userNames[userNames.indexOf(request.user)]),
            "room"
          );
          if (thePrivateRoom == undefined) {
            let roomNumber = createRoomNumber();
            privateRoomArray.push(
              new PrivateRoom(
                [
                  findUser(userNames[userArray.indexOf(socket.id)]),
                  findUser(request.user),
                ],
                roomNumber
              )
            );
            socket.join(`room${roomNumber}`);
            findUser(request.user).socket.join(`room${roomNumber}`);

            socket.emit(
              "roomJoined",
              new Message(
                "System",
                "you are now in a private room",
                findUserColor(userArray[userNames.indexOf(request.user)]),
                findUser(userNames[userArray.indexOf(socket.id)]).avatar
              )
            );

            io.to(findUser(request.user).socket.id).emit(
              "roomJoined",
              new Message(
                "System",
                "You are now in a private room",
                findUserColor(socket.id),
                findUser(userNames[userArray.indexOf(socket.id)]).avatar
              )
            );

            //too big, create functions that are good and small
          } else {
            privateRoomArray = privateRoomArray.map((a) => {
              if (a == thePrivateRoom) {
                a.users.push(findUser(userNames[userArray.indexOf(socket.id)]));
              }
              return a;
            });

            socket.join(`room${thePrivateRoom.roomNumber}`);
            socket.emit(
              "roomJoined",
              new Message(
                "System",
                "you are now in a private room",
                findUserColor(userArray[userNames.indexOf(request.user)]),
                null
              )
            );
          }
        }
      }
    } else {
      socket.emit(
        "inviteAndPriv",
        new Message(
          userNames[userArray.indexOf(socket.id)],
          "There are currently no pending invites",
          findUserColor(socket.id),
          findUser(userNames[userArray.indexOf(socket.id)]).avatar
        )
      );
    }
  });

  socket.on("leavePrivateRoom", () => {
    socket.leave(`room${findPrivateRoom(socket.id).roomNumber}`);
    privateRoomArray.map((a) => {
      if (a.users.includes(findUser(userNames[userArray.indexOf(socket.id)]))) {
        //make function for that
        a.removeUser(findUser(userNames[userArray.indexOf(socket.id)]));
        if (a.checkRoomEmpty()) {
          io.to(a.users[0].socket.id).emit("leaveRoom");
          a.users[0].socket.leave(
            `room${findPrivateRoom(a.users[0].socket.id).roomNumber}`
          );
          privateRoomArray.splice(privateRoomArray.indexOf(a), 1);
        }
      }
    });
  });

  socket.on("newRound", (msg) => {
    if (msg == "continue") {
      ++findGame(findUser(userNames[userArray.indexOf(socket.id)]))
        .continueCounter;

      if (
        findGame(findUser(userNames[userArray.indexOf(socket.id)]))
          .continueCounter == 2
      ) {
        findGame(
          findUser(userNames[userArray.indexOf(socket.id)])
        ).continueCounter = 0;
        io.to(
          findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0]
            .socket.id
        ).emit("gamePlayed", null);
        io.to(
          findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[1]
            .socket.id
        ).emit("gamePlayed", null);
      }
    } else if (msg == "leave") {
      io.to(
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0]
          .socket.id
      ).emit("gameDone", null);
      io.to(
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[1]
          .socket.id
      ).emit("gameDone", null);

      gameArray.splice(
        gameArray.indexOf(
          findGame(findUser(userNames[userArray.indexOf(socket.id)]))
        ),
        1
      );
    }
  });

  socket.on("gameSelected", (RPSseletced) => {
    let winner;
    if (
      socket.id ==
      findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0]
        .socket.id
    ) {
      findGame(
        findUser(userNames[userArray.indexOf(socket.id)])
      ).players[0].selection = RPSseletced;
    } else {
      findGame(
        findUser(userNames[userArray.indexOf(socket.id)])
      ).players[1].selection = RPSseletced;
    }

    if (
      findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0]
        .selection != null &&
      findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[1]
        .selection != null
    ) {
      winner = gameLogic(
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0],
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[1]
      );

      findGame(
        findUser(userNames[userArray.indexOf(socket.id)])
      ).players[0].selection = null;
      findGame(
        findUser(userNames[userArray.indexOf(socket.id)])
      ).players[1].selection = null;

      date = new Date();

      let log = `Game: ${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()} ${date.getHours()}H: ${date.getMinutes()}M: ${date.getSeconds()}S winner: ${winner}\n`;

      writeFile(
        `./public/logs/${date.getFullYear()}-${date.getMonth()+1}-${date.getDate()}events.log`,
        log
      );

      io.to(
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[0]
          .socket.id
      ).emit("roundDone", winner);
      io.to(
        findGame(findUser(userNames[userArray.indexOf(socket.id)])).players[1]
          .socket.id
      ).emit("roundDone", winner);
    }
  });

  socket.on("chat", (msg) => {
    date = new Date();
    let log = `Public Chat:${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()} ${
      date.getHours()
    }H: ${date.getMinutes()}M: ${date.getSeconds()}S user:${
      userNames[userArray.indexOf(socket.id)]
    } length ${msg.length} \n`;

    writeFile(
      `./public/logs/${date.getFullYear()}-${date.getMonth()+1}-${date.getDate()}events.log`,
      log
    );
    socket.broadcast.emit(
      "chat",
      new Message(
        userNames[userArray.indexOf(socket.id)],
        msg,
        findUserColor(socket.id),
        findUser(userNames[userArray.indexOf(socket.id)]).avatar
      )
    );
  });
});

http.listen(PORT, () => {
  console.log(`chat server listening on port ${PORT}`);
});

class Game {
  constructor(numRounds, players, continueCounter) {
    this.numRounds = numRounds;
    this.players = players;
    this.continueCounter = continueCounter;
  }
}

class Invitation {
  constructor(initiator, peopleInvited, type, status) {
    this.initiator = initiator;
    this.peopleInvited = peopleInvited;
    this.type = type;
    this.status = status;
  }
}

class Message {
  constructor(sender, msg, color, avatar) {
    this.sender = sender;
    this.msg = msg;
    this.color = color;
    this.avatar = avatar;
  }
}

class User {
  constructor(
    socket,
    username,
    currentRoom,
    game,
    pendingInvite,
    color,
    avatar,
    selection
  ) {
    this.socket = socket;
    this.username = username;
    this.currentRoom = currentRoom;
    this.game = game;
    this.pendingInvite = pendingInvite;
    this.color = color;
    this.avatar = avatar;
    this.selection = selection;
  }
  createAvatar() {
    this.avatar = [];
    for (let i = 0; i <= 4; ++i) {
      let theAvatar = `https://robohash.org/${this.username}.png?size=60x60&set=set${i}`;
      this.avatar.push(theAvatar);
    }
    return this.avatar;
  }
  newColor() {
    // will have to fix this
    let flag = false;

    if (colorArray.length < 1) {
      colorArray.push(randomColour());
      this.color = colorArray[colorArray.length - 1];
      return this.color;
    } else if (colorArray.length >= 1) {
      while (!flag) {
        let currLength = colorArray.length;
        colorArray[currLength] = randomColour();

        let trueCounter = 0;

        let numColors = colorArray.length - 1;

        for (let i = 0; i < colorArray.length; ++i) {
          if (
            colorArray[i] != colorArray[currLength] &&
            areDifferent(colorArray[i], colorArray[currLength])&&colorArray[i]!="ffffff"
          ) {
            ++trueCounter;
          }
        }

        if (numColors === trueCounter) {
          flag = true;
          this.color = colorArray[currLength];
          return this.color;
        }
      }
    }
  }
}

class PrivateRoom {
  constructor(users, roomNumber) {
    this.users = users;
    this.roomNumber = roomNumber;
  }
  removeUser(user) {
    this.users = this.users.filter((a) => a != user);
    return this.users;
  }

  checkRoomEmpty() {
    if (this.users.length == 1) return true;
    return false;
  }
  //turn the isInPrivateRoom to off
}

function gameLogic(user1, user2) {
  if (user1.selection == user2.selection) {
    return "tie";
  }

  if (user1.selection == "rock") {
    if (user2.selection == "paper") {
      return user2.username;
    } else {
      return user1.username;
    }
  }

  if (user1.selection == "scissors") {
    if (user2.selection == "rock") {
      return user2.username;
    } else {
      return user1.username;
    }
  }
  if (user1.selection == "paper") {
    if (user2.selection == "scissors") {
      return user2.username;
    } else {
      return user1.username;
    }
  }
}

function createRoomNumber() {
  let number = Math.floor(Math.random() * 1000).toString();
  if (Number.parseInt(number) < 10) {
    number = `00${number}`;
  } else if (Number.parseInt(number) < 100) {
    number = `0${number}`;
  }
  return number;
}

function findUserColor(id) {
  let theUser = allUsers.find((a) => id == a.socket.id);
  return theUser.color;
}
function correspondingInvite(request, socket) {
  let theInvite = inviteArray.find(
    (a) =>
      request.type == a.type &&
      request.user ==
        userNames[userArray.indexOf(a.initiator.id)] &&
      a.peopleInvited.includes(userNames[userArray.indexOf(socket.id)])
  );

  return theInvite;
}

function findUser(username) {
  let theUser = allUsers.find(
    (a) => username == a.username
  );
  return theUser;
}

function findInviteDelete(initiate, type) {
  inviteArray = inviteArray.filter((a) => {
    return a.initiator.id != initiate.socket.id && a.type != type;
  });
  return inviteArray;
}

function findPrivateRoom(id) {
  return privateRoomArray.find((a) =>
    a.users.includes(findUser(userNames[userArray.indexOf(id)]))
  );
}

function findGame(user) {
  return gameArray.find((a) => a.players.includes(user));
}

function writeFile(filename, data) {
  fsPromises.appendFile(filename, data).catch(function (err) {
    console.log(err);
  });
}
let areDifferent = (colour1, colour2) => {
  let r1 = colour1.substring(0, 2);
  let g1 = colour1.substring(2, 4);
  let b1 = colour1.substring(4);

  let r2 = colour2.substring(0, 2);
  let g2 = colour2.substring(2, 4);
  let b2 = colour2.substring(4);

  let rnum1 = parseInt(r1, 16);
  let gnum1 = parseInt(g1, 16);
  let bnum1 = parseInt(b1, 16);
  let rnum2 = parseInt(r2, 16);
  let gnum2 = parseInt(g2, 16);
  let bnum2 = parseInt(b2, 16);

  return (
    Math.sqrt(
      (rnum1 - rnum2) ** 2 + (gnum1 - gnum2) ** 2 + (bnum1 - bnum2) ** 2
    ) > 75
  );
};

let randomColour = () => Math.floor(Math.random() * 16777215).toString(16);
