import React, { useContext } from "react";
import MovieContext from "./ContextApi";
export default function AddMovie() {
  const context = useContext(MovieContext);
  let actors = [];
  let genres = [];
  function Actors() {
    let values = document.querySelector(".actors").value;

    actors = values.split(",");
  }

  function Genres() {
    let values = document.querySelector(".genre").value;
    genres = values.split(",");
  }
  function movieAdded(){
   const success = document.querySelector('#successfullyAdded');
 success.innerHTML='Movie successfully added!'
 setTimeout(()=>{success.innerHTML=''},3000)
  }

  function clear() {
    actors = [];
    genres = [];
    document.querySelector(".title").value = "";
    document.querySelector(".genre").value = "";
    document.querySelector(".actors").value = "";
    document.querySelector("#theYear").value = "";
    document.querySelector("#runtime").value = "";
    document.querySelector("#revenue").value = "";
    movieAdded();
    
  } //fix / make more readable

  function errorCheck() {
    let docTitle = document.querySelector(".title").value;
    let docGenre = document.querySelector(".genre").value;
    let docAct = document.querySelector(".actors").value;
    let docYear = document.querySelector("#theYear").value;
    let docRunTime = document.querySelector("#runtime").value;
    let docRevenue = document.querySelector("#revenue").value;
    const actorsAndGenreRegex = /^[ a-zA-Z,-]{1,50}$/;
    const numberRegex = /^[0-9]{1,10}$/;
    const revenueRegex = /^[0-9.]{1,10}$/;
    const yearRegex = /^[0-9]{4}$/;
    let isFormValid = true;

    if (docTitle.trim() === "") {
      isFormValid = false;
      document.querySelector("#err1").innerHTML = "Please Enter a title";
    } else if (docTitle.length >=  25) {
      isFormValid = false;
      document.querySelector("#err1").innerHTML = "Title must be less than 25 characters";
    } else {
      document.querySelector("#err1").innerHTML = "";
    }
    if (docGenre.trim() === "") {
      isFormValid = false;
      document.querySelector("#err2").innerHTML =
        "Please enter at least one genre";
      //return boolean
      // if(docTitle)
    } else if (!actorsAndGenreRegex.test(docGenre)||docGenre.trim().split(',').includes('')||docGenre.lastIndexOf(',')===docGenre.length-1) {
      isFormValid = false;
      document.querySelector("#err2").innerHTML =
        "Only accepts letters , or - and <=50 characters";
    } else {
      document.querySelector("#err2").innerHTML = "";
    }
    if (docAct.trim() === "") {
      isFormValid = false;
      document.querySelector("#err3").innerHTML =
        "Please enter at least one actor";
      //return boolean
      // if(docTitle)
    } else if (!actorsAndGenreRegex.test(docAct)||docAct.trim().split(',').includes('')||docAct.lastIndexOf(',')===docAct.length-1) {
      isFormValid = false;
      document.querySelector("#err3").innerHTML =
        "Format is wrong. Only accepts letters , or - and <=50 characters";
    } else {
      document.querySelector("#err3").innerHTML = "";
    }
    if (!yearRegex.test(docYear)||Number(docYear)<1888) {
      isFormValid = false;
      document.querySelector("#err4").innerHTML = "Please enter a valid year (from 1888)";
    } else {
      document.querySelector("#err4").innerHTML = "";
    }
    if (!numberRegex.test(docRunTime)||Number(docRunTime)<=0) {
      isFormValid = false;
      document.querySelector("#err5").innerHTML =
        "Please enter a valid run time (in minutes) <10 characters";
    } else {
      document.querySelector("#err5").innerHTML = "";
    }
    if (!revenueRegex.test(docRevenue)||Number(docRevenue)<=0) {
      isFormValid = false;
      document.querySelector("#err6").innerHTML =
        "Please enter a valid revenue <10 characters";
    } else {
      document.querySelector("#err6").innerHTML = "";
    }
    return isFormValid;
  }
  const submit = async (e) => {
    e.preventDefault();
    Genres();
    Actors();
    if (errorCheck()) {
      await fetch("/movies", {
        method: "post",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({
          Title: document.querySelector(".title").value,
          Genre: genres,
          Actors: actors,
          Year: Number(document.querySelector("#theYear").value),
          Runtime: Number(document.querySelector("#runtime").value),
          Revenue: Number(document.querySelector("#revenue").value),
        }),
      }).then(() => {
        clear();
        
        context.changePost(`${Math.random()}`);
      });
    }
  };

  return (
    <div id="myForm">
      <form onSubmit={submit}>
        <h2 id="addAMovie">Add a movie to the database</h2>
        <div id="gridForm">
          <label>
            Title:
            <br />
            <input type="text" className="title" />
            <div id="err1" className="err"></div>
          </label>

          <label>
            Genre(s):
            <br /> <input type="text" className="genre" />
            <div id="err2" className="err"></div>
          </label>

          <label>
            Actor(s):
            <br />
            <input type="text" className="actors" />
            <div id="err3" className="err"></div>
          </label>

          <label>
            Year:
            <br />
            <input type="text" id="theYear" />
            <div id="err4" className="err">
              {" "}
            </div>
          </label>

          <label>
            Runtime:
            <br />
            <input type="text" id="runtime" />
            <div id="err5" className="err"></div>
          </label>

          <label>
            Revenue:
            <br />
            <input type="text" id="revenue" />
            <div id="err6" className="err"></div>
          </label>
        </div>
        <input type="submit" id="submit" value="Submit!" />
        <div id="successfullyAdded"></div>
      </form>
    </div>
  );
}
