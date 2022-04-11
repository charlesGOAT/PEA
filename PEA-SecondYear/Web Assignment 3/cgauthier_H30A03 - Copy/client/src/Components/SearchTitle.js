import React, { useContext } from "react";

import MovieContext from "./ContextApi";

export default function SearchTitle() {
  const context = useContext(MovieContext);
  function searchT() {
    context.changeMovie(`/titles/${document.querySelector("#searchT").value}`);
    //fix query
    document.querySelector("#errActorSelect").innerHTML = "";
  }

  return (
    <div id="searchContainer">
      <input
        type="text"
        id="searchT"
        className="searchBar"
        placeholder="Search by title"
      />
      <button
        className="searchMe"
        onClick={() => {
          const titleRegex = /^.{1,25}$/;
          !titleRegex.test(document.querySelector("#searchT").value)
            ? (document.querySelector("#errActorSelect").innerHTML =
                "Please put a valid title name")
            : searchT();
        }}
      >
        Search!
      </button>
      <div id="errActorSelect"></div>
    </div>
  );
}
