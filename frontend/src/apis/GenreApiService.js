import Axios from 'axios';
// http://localhost:8080/api/movie/popular?page=1
const SPRING_API_URL = 'http://localhost:7777/api/v1/genre/';

class MovieApiService {
    getGenreList() {
        return Axios.get(SPRING_API_URL);
    }
}

export default new MovieApiService();
