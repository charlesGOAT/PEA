import React, { useContext } from "react";

import MovieContext from "./ContextApi";

export default function GenreSelect() {
  const context = useContext(MovieContext);
  function searchG() {
    context.changeMovie(`/genres/${document.querySelector("#searchG").value}`);
    document.querySelector("#errActorSelect").innerHTML = "";
  }

  return (
    <div id="searchContainer">
      <input
        type="text"
        id="searchG"
        className="searchBar"
        placeholder="Search by Genre"
      />
      <button
        className="searchMe"
        onClick={() => {
          const genreRegex = /^[ a-z-A-Z-]{1,25}$/;
          !genreRegex.test(document.querySelector("#searchG").value)
            ? (document.querySelector("#errActorSelect").innerHTML =
                "Please put a valid genre name")
            : searchG();
        }}
      >
        Search!
      </button>
      <div id="errActorSelect"></div>
    </div>
  );
}
