import React, { useState, useEffect, useContext } from "react";
import Movie from "./Movie";
import MovieContext from "./ContextApi";

export default function List() {
  const context = useContext(MovieContext);
  const [movieSelected, setMovieSelected] = useState(0);
  const [movie, setMovies] = useState([]);
  const toggleMovieShown = (e) => {
    setMovieSelected((Cc) => {
      Cc!==e.target.id ? (Cc = e.target.id) : (Cc = 0);
      return Cc;
    });
  };

function returnTitle(){
  if(context.movieSearch.substring(1)==='movies')
  return "All Movies";
  else if(context.movieSearch.substring(1,context.movieSearch.lastIndexOf('/'))==='actors')
  return `Movies for actor ${context.movieSearch.substring(context.movieSearch.lastIndexOf('/')+1)}`;
  else if (context.movieSearch.substring(1,context.movieSearch.lastIndexOf('/'))==='genres')
  return `Movies for genre ${context.movieSearch.substring(context.movieSearch.lastIndexOf('/')+1)}`;
  else if(context.movieSearch.substring(1,context.movieSearch.lastIndexOf('/'))==='titles'){
    return `Movies with Title ${context.movieSearch.substring(context.movieSearch.lastIndexOf('/')+1)}`
  }
  else return`Movies for year ${context.movieSearch.substring(context.movieSearch.lastIndexOf('/')+1)}`;

}


  useEffect(() => {
    const fetcher = async () => {
      setMovies(
        await fetch(`${context.movieSearch}`)
          .then((resp) => {
            return resp.json();
          })
          .then((data) => {
            return data;
          })
      );
    };
    fetcher();
  }, [context.movieSearch,context.moviePost]);

  return (
    <div>
   <h3 id="listAll">{returnTitle()}</h3>
      <div id="movieGrid">
        {movie.length===0?'There are no corresponding movies for the search made':''}
        {movie.map((a, index) => (
          <div key={a.Key} className="movieCells">
            {a.Title} <br/> {a.Year}
            <br/>
            <button id={a.Key} className="movieBtn" onClick={toggleMovieShown}>
              Toggle Info
            </button>
            {Number(movieSelected) === a.Key ? (
              <Movie id={a.Key} key={index} />
            ) : (
              ""
            )}{" "}
          </div>
        ))}
      </div>
    </div>
  );
}
