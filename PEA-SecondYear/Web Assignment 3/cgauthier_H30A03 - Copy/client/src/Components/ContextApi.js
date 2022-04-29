import React, { Component } from "react";
const MovieContext = React.createContext({});
export class MovieProvider extends Component {
  state = {
    movieSearch: "/movies",
    changeMovie: (movieSearch) =>
      this.setState({ movieSearch: `${movieSearch}` }),
    moviePost: "",
    changePost: (moviePost) => this.setState({ moviePost: `${moviePost}` }),
  };
  render() {
    return (
      <MovieContext.Provider value={this.state}>
        {this.props.children}
      </MovieContext.Provider>
    );
  }
}
export default MovieContext;
