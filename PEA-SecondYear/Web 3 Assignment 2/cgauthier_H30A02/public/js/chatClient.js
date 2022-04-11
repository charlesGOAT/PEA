const $$ = (selector) => document.querySelector(selector);
const createElement = (newElement) => document.createElement(newElement);
const appendElement = (parent, child) => parent.appendChild(child);

const allUsers = $$("#allUsers");
const start = $$("#mainChat");
const username = $$("#username");
const errorDiv = $$(".errorDiv");
const preGame = $$("#preGame");
const gameDiv = $$("#gameDiv");
const countdownDiv = $$("#countDownDiv");
const welcome = $$("#welcome");
const privChat = $$("#privateRoom");

let avatarCounter = 0;
let avatars = [];
let arrayOfInvites = [];
let myUsername;
let pendingInvite = false;
let isInPrivateRoom = false;
function checkCommand(socket, check) {
  //might cause an error, will have to see for later and can maybe shorten it, will have to check for it

  if (check.trim() === "!!play") {
    socket.emit("globalInvite");
    return true;
  } 
  else if (check.trim() == "!cancel") {
    socket.emit("cancelRequests");
    return true;
  } 
  else if (
    check.substring(0, check.indexOf(" ")) == "!play" ||
    check.substring(0, check.indexOf(" ")) == "!invite"
  ) 
  {
    socket.emit("inviteUser", check);
    return true;
  } 
  else if (check.includes("!accept")) {
    if (check.split(" ").length != 3) {
      socket.emit("acceptRequest", new Request(null, null));
      return true;
    } else {
      let type = check.split(" ")[1].toLowerCase();
      let user = check.split(" ")[2];
      socket.emit("acceptRequest", new Request(type, user));

      return true;
    }
  } 
   else if (check.charAt(0) == "!") {
    socket.emit("privateMsg", check);
    return true;
  } else if (isInPrivateRoom) {
    socket.emit("privateRoomMsg", check);
    return true;
  } else return false;
} //find way to make this smaller

function receiveMsg(msg) {
  let bubble = createElement("div");
  
  bubble.classList.add("textBubble");
  
  let display = createElement("h5");
  let theMsg = msg.msg;
  let user = msg.sender;
  let color = msg.color;

  display.innerHTML = `${user}: ${theMsg}`;
  bubble.style.borderColor = `#${color}`;

  !isInPrivateRoom
    ? appendElement(start, bubble)
    : appendElement(privChat, bubble);

  appendElement(bubble, display);
}

addEventListener("load", () => {
  let socket = io();

  $$("#theButton").addEventListener("click", () => {
    socket.emit("login", username.value.trim());
  });
  //fix private message and put animation tmrw

  socket.on("validUser", () => {
    preGame.classList.add("noDisplay");

    setTimeout(preGame.classList.add("displayNone"), 5000);
    setTimeout($$("#main").classList.remove("displayNone"), 5000);
  });

  socket.on("privChat", (msg) => receiveMsg(msg)); //test for fat arrow

  socket.on("invalidUser", () => {
    errorDiv.innerHTML = "";
    let display = createElement("h5");

    display.innerHTML = `The username as either already been taken or the field is empty. 
    Furthermore, your username cannot be equal to a command from the chat`;

    display.style.color = "red";
    appendElement(errorDiv, display);
  
  });

  socket.on("hi", (msg) => {
    avatars = msg.avatar;
    myUsername = msg.sender;
  
    $$("#avatar").src = avatars[avatarCounter];
    $$("#hi").innerHTML += ` ${myUsername} `;
  
    receiveMsg(msg);
  });

  socket.on("inviteAndPriv", (msg) => {
    receiveMsg(msg);
  });

  socket.on("roomJoined", (msg) => {
    $$("#chat").style.backgroundColor = "rgb(66, 66, 66)";

    isInPrivateRoom = true;
    $$("#title").innerHTML = "Private Chat";
  
    $$("#title").style.color = "rgb(0, 153, 255)"
    start.classList.add("displayNone");

    privChat.classList.remove("displayNone");
    $$("#leavePrivateRoom").classList.remove("displayNone");
    receiveMsg(msg);
  });

  socket.on("gamePlayed", (msg) => {
    if (msg != null) receiveMsg(msg);

    countDown($$("#game"));
  });

  socket.on("showUsers", (users) => {
    allUsers.innerHTML = "";
    users.forEach((a) => {
      let allUser = createElement("p");

      allUser.innerHTML = `${a}`;
      appendElement(allUsers, allUser);

      $$("#msgBox").value = "";
    });
  });

  $$("#sendMsg").addEventListener("click", (e) => {
    if (checkCommand(socket, $$("#msgBox").value)) {
      e.preventDefault();
    } else {
      socket.emit("chat", $$("#msgBox").value);

      e.preventDefault();
    }

    let display = createElement("div");
    display.innerHTML += `${$$("#msgBox").value}`;
    display.classList.add("textBubble");
    display.style.marginRight = "0px";
    display.style.marginLeft = "auto";

    !isInPrivateRoom
      ? appendElement(start, display)
      : appendElement(privChat, display);
    $$("#msgBox").value = "";
    //will have to check for later because to see if it has been sent to the server first or not
  });

  socket.on("chat", (msg) => {
    //will have to verify if good or not
    if (!isInPrivateRoom) receiveMsg(msg);
  });

  socket.on("roundDone", (winner) => {
    countdownDiv.innerText = `The winner is ${winner}`;
    $$("#continue").classList.remove("displayNone");
    $$("#leave").classList.remove("displayNone");
  });

  $$("#leavePrivateRoom").addEventListener("click", (e) => {
    socket.emit("leavePrivateRoom");

    $$("#chat").style.backgroundColor = "white";

    e.target.classList.add("displayNone");
    isInPrivateRoom = false;

    $$("#title").innerHTML = "Main Chat";
    $$("#title").style.color = "black"
    privChat.innerHTML = "";
  
    privChat.classList.add("displayNone");
    start.classList.remove("displayNone");
    e.preventDefault();
  });

  $$("#img1").addEventListener("click", (e) => {
    e.preventDefault();
    countdownDiv.innerText = "waiting for opponent to choose an option";
    socket.emit("gameSelected", "paper");
    gameDiv.classList.add("displayNone");
    
  });

  $$("#img2").addEventListener("click", (e) => {
    e.preventDefault();
    countdownDiv.innerText = "waiting for opponent to choose an option";
    socket.emit("gameSelected", "rock");
    gameDiv.classList.add("displayNone");
   
  });

  $$("#img3").addEventListener("click", (e) => {
    e.preventDefault();
    countdownDiv.innerText = "waiting for opponent to choose an option";
    socket.emit("gameSelected", "scissors");
    gameDiv.classList.add("displayNone");
   
  });

  $$("#continue").addEventListener("click", (e) => {
    socket.emit("newRound", "continue");
    e.target.classList.add("displayNone");
    $$("#leave").classList.add("displayNone");
    e.preventDefault();
  });

  $$("#leave").addEventListener("click", (e) => {
    socket.emit("newRound", "leave");
    e.preventDefault();
  });

  $$("#avatar").addEventListener("click", (e) => {
    ++avatarCounter;
    $$("#avatar").src = avatars[avatarCounter];
    if (avatarCounter == 4) {
      avatarCounter = 0;
    }

    e.preventDefault();
  });

  socket.on("leaveRoom", () => {
    $$("#chat").style.backgroundColor = "white";
    $$("#title").style.color = "black"
    isInPrivateRoom = false;
    $$("#title").innerHTML = "Main Chat";
    privChat.innerHTML = "";

    privChat.classList.add("displayNone");
    start.classList.remove("displayNone");

    $$("#leavePrivateRoom").classList.add("displayNone");
  });

  socket.on("gameDone", () => {
    gameDiv.classList.add("displayNone");
    countdownDiv.innerText = "";
    $$("#leave").classList.add("displayNone");
    $$("#continue").classList.add("displayNone");
  });
});

//game functions
function countDown(element) {
  appendElement(element, countdownDiv);
  countdownDiv.innerText = "";
  setTimeout(() => {
    countdownDiv.innerText = `3...`;
  }, 1000);
  setTimeout(() => {
    countdownDiv.innerText = `2...`;
  }, 2000);
  setTimeout(() => {
    countdownDiv.innerText = `1...`;
  }, 3000);
  setTimeout(() => {
    countdownDiv.innerText = "Choose what you want to pick";
    gameDiv.classList.remove("displayNone");
  }, 4000);
}

class Request {
  constructor(type, user) {
    this.type = type;
    this.user = user;
  }
}
