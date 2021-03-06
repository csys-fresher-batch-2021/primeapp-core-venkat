package in.venkat.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import in.venkat.dao.ShowListDao;
import in.venkat.exceptions.DbException;
import in.venkat.exceptions.EmptyFieldException;
import in.venkat.exceptions.InvalidDetailsException;
import in.venkat.exceptions.InvalidMovieIdException;
import in.venkat.exceptions.InvalidNameException;
import in.venkat.exceptions.InvalidUserIdException;
import in.venkat.exceptions.MovieAlreadyExistsException;
import in.venkat.model.Show;
import in.venkat.util.IdValidationUtil;
import in.venkat.util.Logger;
import in.venkat.util.NameValidationUtil;
import in.venkat.util.ShowDetailsValidationUtil;
import in.venkat.validator.DownloadValidator;
import in.venkat.validator.ValidateSearchDetails;

public class ShowService {
	private ShowService() {
		/**
		 * Adding private constructor
		 */
	}

	/**
	 * This method is used to search movies by giving genre and language
	 * 
	 * @param filmGenre
	 * @param filmLanguage
	 * @return
	 * @throws InvalidDetailsException
	 * @throws InvalidNameException
	 * @throws EmptyFieldException
	 * @throws DbException
	 */
	public static List<Show> searchContents(String filmGenre, String filmLanguage)
			throws EmptyFieldException, InvalidNameException, InvalidDetailsException, DbException {
		boolean isValid;
		List<Show> search = null;

		isValid = ValidateSearchDetails.validateDetails(filmGenre, filmLanguage);
		if (isValid) {
			search = searchMoviesByLanguageAndGenre(filmGenre, filmLanguage);
		}
		return search;
	}

	public static List<Show> searchMoviesByLanguageAndGenre(String filmGenre, String filmLanguage) throws DbException {
		List<Show> primeMovieList;
		List<Show> filteredMovieList = new ArrayList<>();
		primeMovieList = ShowListDao.getShowDetails();
		for (Show show : primeMovieList) {
			if (show.getMovieGenre().equalsIgnoreCase(filmGenre.trim())
					&& show.getMovieLanguage().equalsIgnoreCase(filmLanguage.trim()))
				filteredMovieList.add(show);
		}
		return filteredMovieList;

	}

	/**
	 * This method is used to search movies by membership
	 * 
	 * @param membership
	 * @return
	 * @throws DbException
	 * @throws InvalidDetailsException
	 * @throws InvalidNameException
	 * @throws EmptyFieldException
	 * 
	 */
	public static List<Show> searchMoviesByMembership(String membership)
			throws DbException, EmptyFieldException, InvalidNameException, InvalidDetailsException {
		ValidateSearchDetails.validateDetails(membership);
		List<Show> primeMovieList;
		List<Show> filteredMovieList = new ArrayList<>();
		primeMovieList = ShowListDao.getShowDetails();
		for (Show show : primeMovieList) {
			if (show.getMembership().equalsIgnoreCase(membership.trim())) {
				filteredMovieList.add(show);
				Logger.log(show);
			}
		}
		return filteredMovieList;

	}

	/**
	 * This method is used to search movies by the released year
	 * 
	 * @param year
	 * @return
	 * @throws DbException
	 */
	public static List<Show> searchMoviesByYear(int year) throws DbException {
		List<Show> primeMovieList;
		List<Show> filteredMovieList = new ArrayList<>();
		if (year > 1950 && year <= 2021) {
			primeMovieList = ShowListDao.getShowDetails();
			for (Show show : primeMovieList) {
				if (show.getMovieYear() == year) {
					filteredMovieList.add(show);
					Logger.log(show);
				}

			}
		}
		return filteredMovieList;
	}

	/**
	 * This method is used to search movies by language
	 * 
	 * @param filmLanguage
	 * @return
	 * @throws DbException
	 * @throws InvalidDetailsException
	 * @throws InvalidNameException
	 * @throws EmptyFieldException
	 */
	public static List<Show> searchMoviesByLanguage(String filmLanguage)
			throws DbException, EmptyFieldException, InvalidNameException, InvalidDetailsException {
		ValidateSearchDetails.validateDetails(filmLanguage);

		List<Show> primeMovieList;
		List<Show> filteredMovieList = new ArrayList<>();

		primeMovieList = ShowListDao.getShowDetails();
		for (Show show : primeMovieList) {
			if (show.getMovieLanguage().equalsIgnoreCase(filmLanguage.trim())) {
				filteredMovieList.add(show);
				Logger.log(show);

			}
		}
		return filteredMovieList;

	}

	/**
	 * This method is used to display all movies
	 * 
	 * @param filmCategory
	 * @return
	 * @throws DbException
	 * @throws InvalidDetailsException
	 * @throws InvalidNameException
	 * @throws EmptyFieldException
	 */
	public static List<Show> displayAllMovies(String filmCategory)
			throws DbException, EmptyFieldException, InvalidNameException, InvalidDetailsException {
		ValidateSearchDetails.validateDetails(filmCategory);

		List<Show> primeMovieList;
		List<Show> filteredMovieList = new ArrayList<>();

		primeMovieList = ShowListDao.getShowDetails();
		for (Show show : primeMovieList) {
			if (show.getMovieCategory().equalsIgnoreCase(filmCategory.trim())) {
				filteredMovieList.add(show);
				Logger.log(show);
			}
		}

		return filteredMovieList;
	}

	/**
	 * This method is used to add movies or shows
	 * 
	 * @param genre
	 * @param name
	 * @param year
	 * @param language
	 * @param category
	 * @param membership
	 * @param grade
	 * @return
	 * @throws EmptyFieldException
	 * @throws InvalidNameException
	 * @throws InvalidDetailsException
	 * @throws DbException
	 * @throws MovieAlreadyExistsException
	 */
	public static boolean addShows(String genre, String name, int year, String language, String category,
			String membership, String grade, String status) throws EmptyFieldException, InvalidNameException,
			InvalidDetailsException, DbException, MovieAlreadyExistsException {
		boolean added = false;
		boolean genreValid = NameValidationUtil.validateName(genre);
		boolean nameValid = NameValidationUtil.validateName(name);
		boolean yearValid = ShowDetailsValidationUtil.isYearValid(year);
		boolean languagevalid = NameValidationUtil.validateName(language);
		boolean categoryValid = NameValidationUtil.validateName(category);
		boolean memberShipValid = ShowDetailsValidationUtil.validateMembership(membership);
		boolean gradeValid = ShowDetailsValidationUtil.gradeValidation(grade);
		boolean statusValid = ShowDetailsValidationUtil.statusValidation(status);
		int likes = 0;
		boolean isMoviePresent = isMoviePresent(name, year, language);
		if (genreValid && nameValid && yearValid && languagevalid && categoryValid && memberShipValid && gradeValid
				&& statusValid && !isMoviePresent) {
			Show show = new Show(genre, name, year, language, category, membership, grade, status, likes);
			ShowListDao.addMovies(show);
			added = true;
		} else {
			throw new MovieAlreadyExistsException("movie already exists");
		}
		return added;

	}

	/**
	 * This method checks whether the movie is already present
	 */
	public static boolean isMoviePresent(String name, int year, String language) throws DbException {
		boolean present = false;
		List<Show> showDetails = ShowListDao.getShowDetails();
		for (Show show : showDetails) {
			if (show.getMovieName().equals(name) && show.getMovieYear() == year
					&& show.getMovieLanguage().equalsIgnoreCase(language.trim())) {

				present = true;

			}
		}
		return present;

	}

	/**
	 * This method delete the movie from shows from id
	 * 
	 * @param movieId
	 * @return
	 * @throws EmptyFieldException
	 * @throws InvalidNameException
	 * @throws InvalidDetailsException
	 * @throws DbException
	 * @throws InvalidMovieIdException
	 */
	public static boolean deleteMovie(int movieId) throws DbException, EmptyFieldException, InvalidMovieIdException {
		boolean deleted = false;
		List<Show> show = ShowListDao.getShowDetails();

		for (Show showId : show) {
			if (showId.getId() == movieId && IdValidationUtil.validateId(movieId)) {
				ShowListDao.deleteMovies(movieId);
				deleted = true;
				break;
			}

		}
		if (!deleted) {
			throw new InvalidMovieIdException("movie id does not exists");
		}
		return deleted;
	}

	/**
	 * This method is used to update the movie status to prime and non prime
	 * 
	 * @param movieId
	 * @return
	 * @throws DbException
	 * @throws InvalidMovieIdException
	 */
	public static boolean primeStatusUpdate(int movieId) throws DbException, InvalidMovieIdException {
		boolean updated = false;
		boolean present = isMovieIdPresent(movieId);
		if (present) {
			String status = getMovieStatus(movieId);
			if (status.equalsIgnoreCase("prime")) {
				ShowListDao.updatePrimeStatus(movieId, "non prime");
				updated = true;
			} else if (status.equalsIgnoreCase("non prime")) {
				ShowListDao.updatePrimeStatus(movieId, "prime");
				updated = true;
			}

		} else {
			throw new InvalidMovieIdException("movie id does no exists");
		}
		return updated;

	}

	/**
	 * This method is used to check the movie id id present or not
	 * 
	 * @param movieId
	 * @return
	 * @throws DbException
	 * @throws InvalidMovieIdException
	 */
	public static boolean isMovieIdPresent(int movieId) throws DbException, InvalidMovieIdException {
		boolean present = false;

		List<Show> show = ShowListDao.getShowDetails();
		for (Show showId : show) {
			if (showId.getId() == movieId) {
				present = true;
			}
		}
		if (!present) {
			throw new InvalidMovieIdException("movie Id does not exists");
		}
		return present;
	}

	/**
	 * This method is used to get the movie status
	 * 
	 * @param movieId
	 * @return
	 * @throws DbException
	 */
	public static String getMovieStatus(int movieId) throws DbException {
		String status = null;
		boolean present = false;
		List<Show> show = ShowListDao.getShowDetails();
		for (Show showId : show) {
			if (showId.getId() == movieId) {
				status = showId.getMembership();
				present = true;
			}
		}
		if (!present) {
			throw new NullPointerException("Id does not exists");
		}
		return status;

	}

	/**
	 * This method is used to check whether the movie is already added by the user
	 * 
	 * @param movieId
	 * @param userId
	 * @return
	 * @throws DbException
	 * @throws InvalidMovieIdException
	 */
	public static boolean isFavoriteMovieExixts(int movieId, String userId)
			throws DbException, InvalidMovieIdException {
		boolean isExists = false;
		List<Show> favorites = ShowListDao.getFavoriteMovie(userId);
		for (Show favorite : favorites) {
			if (favorite.getId() == movieId) {
				isExists = true;
			}
		}
		if (isExists) {
			throw new InvalidMovieIdException("movie already added to favorites");
		}
		return isExists;

	}

	/**
	 * This method is used to add movie to favorites list
	 * 
	 * @param userId
	 * @param movieId
	 * @return
	 * @throws DbException
	 * @throws InvalidUserIdException
	 * @throws InvalidMovieIdException
	 */
	public static boolean addToFavorites(String userId, int movieId)
			throws DbException, InvalidUserIdException, InvalidMovieIdException {
		boolean isAdded = false;
		boolean validUser = UserService.isValidUser(userId);
		boolean validMovieId = isMovieIdPresent(movieId);
		boolean isAlreadyExists = isFavoriteMovieExixts(movieId, userId);
		if (validMovieId && validUser && !isAlreadyExists) {
			List<Show> favorite = getFavorites(userId, movieId);
			for (Show favorites : favorite) {
				ShowListDao.addFavoriteMovies(favorites);
				ShowListDao.updateLikes(movieId);
				isAdded = true;
			}
		}
		return isAdded;
	}

	/**
	 * This method is used to get the favorite movie list
	 * 
	 * @param userId
	 * @param movieId
	 * @return
	 * @throws DbException
	 */
	public static List<Show> getFavorites(String userId, int movieId) throws DbException {
		List<Show> favorites = new ArrayList<>();
		List<Show> show = ShowListDao.getShowDetails();

		for (Show showDetail : show) {
			if (showDetail.getId() == movieId) {
				String genre = showDetail.getMovieGenre();
				String name = showDetail.getMovieName();
				int year = showDetail.getMovieYear();
				String language = showDetail.getMovieLanguage();
				String category = showDetail.getMovieCategory();
				String membership = showDetail.getMembership();
				String grade = showDetail.getMovieGrade();
				String status = showDetail.getStatus();

				favorites.add(
						new Show(userId, movieId, genre, name, year, language, category, membership, grade, status));
			}
		}
		return favorites;

	}

	/**
	 * This method is used to view favorite movies of the user
	 * 
	 * @param userId
	 * @return
	 * @throws DbException
	 * @throws InvalidUserIdException
	 */
	public static List<Show> viewFavoriteMovies(String userId) throws DbException, InvalidUserIdException {
		boolean validUser = UserService.isValidUser(userId);
		List<Show> favorites = new ArrayList<>();
		if (validUser) {
			favorites = ShowListDao.getFavoriteMovie(userId);

		}
		return favorites;

	}

	/**
	 * This method is used to get the trending movies
	 * 
	 * @param movieId
	 * @return
	 * @throws DbException
	 */
	public static List<Show> getTrendingMovies() throws DbException {
		List<Show> trendingMovies = new ArrayList<>();
		for (Show trending : ShowListDao.getTrendingMovies()) {
			if (trending.getLikes() != 0) {
				trendingMovies.add(trending);
			}
		}

		return trendingMovies;

	}

	/**
	 * This method is used to search movies in the movie list
	 * 
	 * @param movieName
	 * @return
	 * @throws DbException
	 * @throws InvalidNameException
	 * @throws EmptyFieldException
	 */
	public static List<Show> searchByMovieName(String movieName)
			throws DbException, EmptyFieldException, InvalidNameException {
		boolean valid = NameValidationUtil.validateName(movieName);
		List<Show> searchResults = null;
		if (valid) {
			String finalMovieName = movieName.toLowerCase();
			searchResults = ShowListDao.getShowDetails().stream()
					.filter(movie -> movie.getMovieName().toLowerCase().contains(finalMovieName))
					.collect(Collectors.toList());

		}
		return searchResults;

	}

	/**
	 * This method is used to set the preferences and displays only the preferred
	 * language movies
	 * 
	 * @param preferredLanguage
	 * @return
	 * @throws DbException
	 * @throws InvalidDetailsException
	 */
	public static List<Show> getPreferredMoviesByLanguage(String preferredLanguage)
			throws DbException, InvalidDetailsException {
		List<Show> preferredTrending = new ArrayList<>();
		List<Show> trendingMovieList = ShowListDao.getTrendingMovies();
		boolean valid = false;
		for (Show trending : trendingMovieList) {
			if (trending.getMovieLanguage().equalsIgnoreCase(preferredLanguage.trim())) {
				preferredTrending.add(trending);
				valid = true;
			}
		}
		if (!valid) {
			throw new InvalidDetailsException("no movies in this preference !");
		}
		Logger.log(preferredTrending);
		return preferredTrending;

	}

	/**
	 * This method is used to switch to kids zone
	 * 
	 * @param userId
	 * @param zone
	 * @return
	 * @throws DbException
	 * @throws InvalidUserIdException
	 * @throws InvalidDetailsException
	 */
	public static List<Show> switchToKidsZone(String userId, String zone)
			throws DbException, InvalidUserIdException, InvalidDetailsException {
		List<Show> kidsMovies = new ArrayList<>();
		List<Show> shows = ShowListDao.getShowDetails();
		boolean validUser = UserService.isValidUser(userId);
		boolean isRechargedExpired = UserService.isRechargeNotExpired(userId);
		if (validUser && !isRechargedExpired) {
			if (zone.equalsIgnoreCase("kids")) {
				for (Show kids : shows) {
					if (kids.getMovieGenre().equalsIgnoreCase("comedy")
							|| kids.getMovieGenre().equalsIgnoreCase("adventure")
							|| kids.getMovieGenre().equalsIgnoreCase("kids")
							|| kids.getMovieGrade().equalsIgnoreCase("u")
							|| kids.getMovieGrade().equalsIgnoreCase("v")) {
						int movieId = kids.getId();
						String genre = kids.getMovieGenre();
						String name = kids.getMovieName();
						int year = kids.getMovieYear();
						String language = kids.getMovieLanguage();
						String category = kids.getMovieCategory();
						String membership = kids.getMembership();
						String grade = kids.getMovieGrade();
						String status = kids.getStatus();

						kidsMovies.add(
								new Show(movieId, genre, name, year, language, category, membership, grade, status));
					}
				}
			} else {
				throw new InvalidDetailsException("invalid details");
			}
		}
		Logger.log(kidsMovies);
		return kidsMovies;
	}

	/**
	 * This method is used to add downloads by user
	 * 
	 * @param userId
	 * @param movieId
	 * @return
	 * @throws DbException
	 * @throws InvalidUserIdException
	 * @throws InvalidMovieIdException
	 * @throws MovieAlreadyExistsException
	 */
	public static boolean addToDownloads(String userId, int movieId)
			throws DbException, InvalidUserIdException, InvalidMovieIdException, MovieAlreadyExistsException {
		boolean valid = false;
		boolean validUser = UserService.isValidUser(userId);
		boolean validMovieId = isMovieIdPresent(movieId);
		boolean validDownload = isAreadyDownload(movieId);
		if (validUser && validMovieId && validDownload) {
			List<Show> download = getDownloads(userId, movieId);
			for (Show downloads : download) {
				ShowListDao.saveDownload(downloads);
				valid = true;
			}
		}
		return valid;
	}

	/**
	 * This method is used to check the movie already download
	 * 
	 * @param userId
	 * @param movieId
	 * @return
	 * @throws DbException
	 * @throws MovieAlreadyExistsException
	 */
	public static boolean isAreadyDownload(int movieId) throws DbException, MovieAlreadyExistsException {
		boolean valid = false;
		List<Show> downloads = ShowListDao.getDownloads();
		for (Show expire : downloads) {
			if (expire.getId() == movieId) {
				valid = DownloadValidator.isDownloadedMoviesExpired(expire.getExpireDate());
			} else {
				valid = true;
			}
		}
		return valid;
	}

	/**
	 * This method is used to get the download movies
	 * 
	 * @param userId
	 * @param movieId
	 * @return
	 * @throws DbException
	 */
	public static List<Show> getDownloads(String userId, int movieId) throws DbException {
		List<Show> download = new ArrayList<>();
		List<Show> show = ShowListDao.getShowDetails();
		for (Show movie : show) {
			if (movie.getId() == movieId) {
				String genre = movie.getMovieGenre();
				String name = movie.getMovieName();
				int year = movie.getMovieYear();
				String language = movie.getMovieLanguage();
				String category = movie.getMovieCategory();
				String membership = movie.getMembership();
				String grade = movie.getMovieGrade();
				String status = movie.getStatus();
				LocalDate downloadDate = LocalDate.now();
				LocalDate expiryDate = downloadDate.plusDays(3);

				download.add(new Show(userId, movieId, genre, name, year, language, category, membership, grade, status,
						downloadDate, expiryDate));
			}
		}
		return download;
	}

}
