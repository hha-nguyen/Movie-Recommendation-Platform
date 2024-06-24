import Axios from "axios";

const API_URL = "http://localhost:7777/api/v1/comments/"; //spring boot back-end url

class CommentApiService {
  writeMovieComment(comment) {
    const token = localStorage.getItem('jwtToken');
    console.log("comment: ", comment);
    return Axios.post(API_URL , comment, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }); 
  }
  getCommentListByMovie(movieId) {
    const token = localStorage.getItem('jwtToken');
    return Axios.get(API_URL, {
      params: {
        movieId: movieId
      },
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  deleteComment(id) {
    const token = localStorage.getItem('jwtToken');
    return Axios.delete(API_URL + "/delete/" + id, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }
}

export default new CommentApiService();
