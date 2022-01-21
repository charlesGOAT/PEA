import React, { useEffect, useState } from "react";
import Actor from "./Actor";
import Genre from "./Genre";

export default function Movie(id, key) {
  const [movie, setMovie] = useState([]);

  useEffect(() => {
    const fetcher = async () => {
      setMovie(
        await fetch(`/movies/${id.id}`)
          .then((resp) => {
            console.log(resp);
            return resp.json();
          })
          .then((data) => {
            console.log(data);
            return data;
          })
      );
    };
    fetcher();
  },[id.id] );

  return (
    <div>
      <h3>Movie Details</h3>

      <div key={id}>
        {" "}
        Runtime: {movie.Runtime} <br/>Revenue: {movie.Revenue}{" "}
       <div id="floatRight"><Actor actors={movie.Actors} /></div>  <Genre genres={movie.Genre} />
      </div>
    </div>
  );
}
