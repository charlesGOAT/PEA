const express = require("express");
const path = require("path");
const app = express();
const fs = require("fs");
const fsPromises = fs.promises;
const WEBROOT = path.join(__dirname, "public");
const PORT = 8888;

app.use(express.static(WEBROOT));

app.use(express.json());
app.route("/movies").get((req, res) => {
  fsPromises.readFile("./data/IMDBmovieData.json").then((resp) => {
    let moviesArray = JSON.parse(resp);

    moviesArray = moviesArray.sort((a, b) =>
      a.Title.toString().localeCompare(b.Title.toString())
    );
    let displayMovies = moviesArray.map((a) => {
      return {
        Key: a.Key,
        Title: a.Title,
        Year: a.Year,
      };
    });
    res.send(displayMovies);
  }); //will have to fix that.
});
app.route("/movies/:id").get((req, res) => {
  fsPromises.readFile("./data/IMDBmovieData.json").then((resp) => {
    let movieArray = JSON.parse(resp);
    let keyArray = movieArray.map((a) => a.Key);
    if (keyArray.indexOf(Number(req.params.id)) !== -1) {
      let theMovie = movieArray.find((a) => Number(req.params.id) == a.Key);
      res.json(theMovie);
    }
  }); //answer in lab 07
  //will have to fix that.
});

app.route("/actors/:name").get((req, res) => {
  fsPromises.readFile("./data/IMDBmovieData.json").then((resp) => {
    let movieArray = JSON.parse(resp);
    movieArray = movieArray.sort((a, b) =>
      a.Title.toString().localeCompare(b.Title.toString())
    );
    movieArray = movieArray.map((a) => {
      let returnBoolean = false;
      a.Actors.map((b) => {
        if (b.toLowerCase().includes(req.params.name.toLowerCase())) {
          returnBoolean = true;
        }
      });
      if (returnBoolean) {
        return a;
      }
    });
    movieArray = movieArray.filter((a) => a !== undefined);
    res.json(movieArray);
  });
});

app.use(express.json());

app.route("/years/:year").get((req, res) => {
  fsPromises.readFile("./data/IMDBmovieData.json").then((resp) => {
    let movieArray = JSON.parse(resp);

    movieArray = movieArray.filter((a) => a.Year == req.params.year);
    movieArray = movieArray.sort((a, b) =>
      a.Title.toString().localeCompare(b.Title.toString())
    );
    let displayMovies = movieArray.map((a) => {
      return {
        Key: a.Key,
        Title: a.Title,
      };
    });
    res.json(displayMovies);
  });
});

app.route("/movies").post((req, res) => {
  fsPromises.readFile("./data/IMDBmovieData.json").then((resp) => {
    let movieArray = JSON.parse(resp);

    let keyArray = movieArray.map((a) => a.Key);
    let maxMovie = Math.max(...keyArray);
    let newMovie = {
      Key: maxMovie + 1,
      Title: req.body.Title,
      Genre: req.body.Genre,
      Actors: req.body.Actors,
      Year: req.body.Year,
      Runtime: req.body.Runtime,
      Revenue: req.body.Revenue,
    };
    movieArray.push(newMovie);
    fs.writeFile(
      "./data/IMDBmovieData.json",
      JSON.stringify(movieArray, null, 2),
      function (err) {
        if (err) throw err;
      }
    );
    res.json(true);
  });
});

app.route("/genres/:genre").get((req, res) => {
  fsPromises.readFile("./data/IMDBmovieData.json").then((resp) => {
    let movieArray = JSON.parse(resp);
    movieArray = movieArray.sort((a, b) =>
      a.Title.toString().localeCompare(b.Title.toString())
    );
    movieArray = movieArray.map((a) => {
      let returnBoolean = false;
      a.Genre.map((b) => {
        if (b.toLowerCase().includes(req.params.genre.toLowerCase())) {
          returnBoolean = true;
        }
      });
      if (returnBoolean) {
        return a;
      }
    });
    movieArray = movieArray.filter((a) => a !== undefined);
    res.json(movieArray);
  });
});

app.route("/titles/:title").get((req, res) => {
  fsPromises.readFile("./data/IMDBmovieData.json").then((resp) => {
    let movieArray = JSON.parse(resp);
    movieArray = movieArray.sort((a, b) =>
      a.Title.toString().localeCompare(b.Title.toString())
    );
    movieArray = movieArray.filter((a) =>
      a.Title.toString().toLowerCase().includes(req.params.title.toLowerCase())
    );
    movieArray = movieArray.filter((a) => a !== undefined);
    res.json(movieArray);
  });
});

app.listen(PORT, () => {
  console.log(PORT);
});
