import Axios from 'axios';

const SPRING_API_URL = 'http://localhost:7777/api/v1/movie/';

class MovieApiService {
  getMoviesByGenre(genre) {
    const token = localStorage.getItem('jwtToken');
    return Axios.get(SPRING_API_URL + 'genre/' + genre, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  getMovieDetails(id) {
    const token = localStorage.getItem('jwtToken');
    return Axios.get(SPRING_API_URL + id, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  getCredits(id) {
    const token = localStorage.getItem('jwtToken');
    return Axios.get(SPRING_API_URL + 'credits?id=' + id, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  getSimilarMovies(id) {
    const token = localStorage.getItem('jwtToken');
    return Axios.get(SPRING_API_URL + 'similar/' + id, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  getAllMovies(page) {
    const token = localStorage.getItem('jwtToken');
    return Axios.get(SPRING_API_URL + 'toprated', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  getPopularMovies(page) {
    const token = localStorage.getItem('jwtToken');
    return Axios.get(SPRING_API_URL + 'popular', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }
}

export default new MovieApiService();
