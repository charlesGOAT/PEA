import "../styles/App.css";
import React, { useContext, useState} from "react";
import List from "./List";
import AddMovie from "./AddMovie";
import ActorSelect from "./ActorSelect"
import YearSelect from "./YearSelect";
import MovieContext from "./ContextApi";
import GenreSelect from "./GenreSelect";
import SearchTitle from "./SearchTitle";

function App() {
  const context = useContext(MovieContext) 
  const [searchCategory, toggleCategory] = useState('year');
  const [addMovie,toggleMovieAdd] = useState(false);
  
  const changeCategory = (e) =>{
toggleCategory(()=>e.target.id);
  }

const add = ()=>{
  toggleMovieAdd(()=>!addMovie);
}
  return (

    <div>
  

      <section>
      
     
        <div>
          <h2 className="Title">
            Charles' Movie Collection
            <br />
            <span className="otherPhrase">
              Check movies and search for{" "}
              <span className="wordInYellow">them</span>
              <br />
              <p className="paragraph">
                Browse over
                <span className="wordInYellow"> 1000</span> movies and check
                their different actors, genres, and more! <br />{" "}
                <span className="displayWhenBig">
                  Enjoy seeing your favorite movies in this massive database!
                </span>{" "}
              </p>
            </span>
          </h2>
        </div>
        <br/>
        
        
        </section>
        <br/>
        <br/>
        <div id="inline">
        <button id="all" onClick={() => context.changeMovie(`/movies`)}>
       All Movies
      </button>
       <button id='actor' onClick = {changeCategory}>Search by Actor</button>
       <button id='year' onClick= {changeCategory}>Search by Year</button>
       <button id='genre' onClick= {changeCategory}>Search by Genre</button>
       <button id='title' onClick= {changeCategory}>Search by Title</button>
       <button id="postMovie" onClick={add}>Add Movie</button>
     
       </div>
        {searchCategory==='actor'?<ActorSelect/>:(searchCategory==='year'?<YearSelect/>:(searchCategory==='genre'?<GenreSelect/>:<SearchTitle/>))}
       
        {addMovie?<AddMovie />:''}
        <List/>
       
      
    </div>
    

  );
}

export default App;
