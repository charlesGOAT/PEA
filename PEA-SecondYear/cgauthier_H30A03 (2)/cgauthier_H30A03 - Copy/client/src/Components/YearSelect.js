import React, { useContext } from "react";

import MovieContext from "./ContextApi";

export default function YearSelect() {
  const context = useContext(MovieContext);

  function searchY() {
    context.changeMovie(`/years/${document.querySelector("#searchY").value}`);
    document.querySelector("#errYearS").innerHTML = "";
  }

  return (
    <div id="searchContainer">
      <input
        type="text"
        id="searchY"
        className="searchBar"
        placeholder="Search by year"
      />
      <button
        className="searchMe"
        onClick={() => {
          const yearRegex = /^[0-9]{4}$/;
          !yearRegex.test(document.querySelector("#searchY").value) ||
          Number(document.querySelector("#searchY").value) < 1888
            ? (document.querySelector("#errYearS").innerHTML =
                "Please put a valid year (from 1888)")
            : searchY();
        }}
      >
        Search!
      </button>
      <div id="errYearS"></div>
    </div>
  );
}
