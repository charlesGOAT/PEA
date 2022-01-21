import React, { useContext } from "react";

import MovieContext from "./ContextApi";

export default function ActorSelect() {
  const context = useContext(MovieContext);
  function searchA() {
    context.changeMovie(`/actors/${document.querySelector("#searchA").value}`);
    document.querySelector("#errActorSelect").innerHTML = "";
  }

  return (
    <div id="searchContainer">
      <input
        type="text"
        id="searchA"
        className="searchBar"
        placeholder="Search by actor"
      />
      <button
        className="searchMe"
        onClick={() => {
          const actorRegex = /^[ a-z-A-Z-]{1,25}$/;
          !actorRegex.test(document.querySelector("#searchA").value)
            ? (document.querySelector("#errActorSelect").innerHTML =
                "Please put a valid actor name")
            : searchA();
        }}
      >
        Search!
      </button>
      <div id="errActorSelect"></div>
    </div>
  );
}
