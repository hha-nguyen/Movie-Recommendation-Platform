import Axios from 'axios';

const API_URL = 'http://localhost:7777/user/v1/mylist';

class FavoriteMovieApiService {
    fetchMovie(userId) {
        return Axios.get(API_URL + '/allmovies/' + userId);
    }

    // fetchMovie() {
    //     return Axios.get(API_URL);
    // }
    
    addMovie(movie) {
        return Axios.post(API_URL+'/add', movie);
    }
    
    removeMovie(id) {
        return Axios.delete(API_URL + '/delete/' + id);
    }

    /*removeMovie2(movieId) {
        return Axios.delete(API_URL + '/' + movieId);
    }

    isMovie(movieId) {
        return Axios.get(API_URL + '/' + movieId);
    }*/

}

export default new FavoriteMovieApiService();
