import React from "react";

export default function Actor(actors) {

    return (

<div>
        
     
      <h4>The cast for this movie is:</h4>

      {actors.actors !== undefined ? (
        <ul>
          {actors.actors.map((a) => {
            return <li key={a}>{a}</li>;
          })}
        </ul>
      ) : (
        ""
      )}
    </div>
  );
}
