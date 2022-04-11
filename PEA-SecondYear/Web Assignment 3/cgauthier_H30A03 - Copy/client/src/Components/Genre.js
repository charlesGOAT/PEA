
import React from "react";


export default function Genre(genres){
    return (<div>
<h4>The genres for this movie are:</h4>
{genres.genres!==undefined?<ul>{genres.genres.map((a)=><li key={a}>{a}</li>)}</ul>:""}
    </div>)
}