package in.venkat.service;

import java.sql.SQLException;
import java.util.List;

import in.venkat.dao.ShowListDao;
import in.venkat.model.Show;
import in.venkat.validator.ValidateSearchDetails;

public class ShowService {
	private ShowService() {
		/**
		 * Adding private constructor
		 */
	}

	private static final String MOVIE_ID = " MOVIE ID :";
	private static final String MOVIE_GENRE = " MOVIE GENRE :";
	private static final String MOVIE_NAME = " MOVIE NAME :";
	private static final String MOVIE_YEAR = " MOVIE YEAR :";
	private static final String MOVIE_LANGUAGE = " MOVIE LANGUAGE :";
	private static final String CATEGORY = " CATEGORY :";
	private static final String MEMBERSHIP = " MEMBERSHIP :";
	private static final String MOVIE_GRADE = " GRADE :";

	/**
	 * This method is used to search movies by giving genre and language details
	 * 
	 * @param filmGenre
	 * @param filmLanguage
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean searchContents(String filmGenre, String filmLanguage) throws Exception {
		boolean isValid = ValidateSearchDetails.validateDetails(filmGenre, filmLanguage);
		boolean search = false;
		if (isValid) {
			search = searchMoviesByLanguage(filmGenre, filmLanguage);

		}
		return search;
	}

	/**
	 * 
	 * @param filmGenre
	 * @param filmLanguage
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean searchMoviesByLanguage(String filmGenre, String filmLanguage) throws Exception {
		List<Show> primeMovieList = ShowListDao.getShowDetails();
		boolean searchDone = false;
		for (Show show : primeMovieList) {
			if (show.getMovieGenre().equalsIgnoreCase(filmGenre.trim())
					&& show.getMovieLanguage().equalsIgnoreCase(filmLanguage.trim())) {
				System.out.println(MOVIE_GENRE + filmGenre + MOVIE_LANGUAGE + filmLanguage);
				System.out.println(MOVIE_ID + show.getId() + MOVIE_GENRE + show.getMovieGenre() + MOVIE_NAME
						+ show.getMovieName() + MOVIE_YEAR + show.getMovieYear() + MOVIE_LANGUAGE
						+ show.getMovieLanguage() + CATEGORY + show.getMovieCategory() + MEMBERSHIP
						+ show.getMembership() + MOVIE_GRADE + show.getMovieGrade());
				searchDone = true;

			}

		}
		return searchDone;
	}

	/**
	 * This method is used to search movies by membership
	 * 
	 * @param membership
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean searchMoviesByMembership(String membership) throws Exception {
		List<Show> primeMovieList = ShowListDao.getShowDetails();
		boolean searchDone = false;
		for (Show show : primeMovieList) {
			if (show.getMembership().equalsIgnoreCase(membership.trim())) {
				System.out.println(MEMBERSHIP + membership);
				System.out.println(MOVIE_ID + show.getId() + MOVIE_GENRE + show.getMovieGenre() + MOVIE_NAME
						+ show.getMovieName() + MOVIE_YEAR + show.getMovieYear() + MOVIE_LANGUAGE
						+ show.getMovieLanguage() + CATEGORY + show.getMovieCategory() + MEMBERSHIP
						+ show.getMembership() + MOVIE_GRADE + show.getMovieGrade());
				searchDone = true;

			}
		}
		return searchDone;
	}

	/**
	 * This method is used to search movies by the released year
	 * 
	 * @param year
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean searchMoviesByYear(int year) throws Exception {
		List<Show> primeMovieList = ShowListDao.getShowDetails();
		boolean searchByYear = false;
		for (Show show : primeMovieList) {
			if (show.getMovieYear() == year) {
				System.out.println(MOVIE_YEAR + year);
				System.out.println(MOVIE_ID + show.getId() + MOVIE_GENRE + show.getMovieGenre() + MOVIE_NAME
						+ show.getMovieName() + MOVIE_YEAR + show.getMovieYear() + MOVIE_LANGUAGE
						+ show.getMovieLanguage() + CATEGORY + show.getMovieCategory() + MEMBERSHIP
						+ show.getMembership() + MOVIE_GRADE + show.getMovieGrade());
				searchByYear = true;

			}
		}
		return searchByYear;
	}

	/**
	 * This method is used to search movies by language
	 * 
	 * @param filmLanguage
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean searchMoviesByLanguage(String filmLanguage) throws Exception {
		List<Show> primeMovieList = ShowListDao.getShowDetails();
		boolean searchDone = false;
		for (Show show : primeMovieList) {
			if (show.getMovieLanguage().equalsIgnoreCase(filmLanguage.trim())) {
				System.out.println(MOVIE_LANGUAGE + filmLanguage);
				System.out.println(MOVIE_ID + show.getId() + MOVIE_GENRE + show.getMovieGenre() + MOVIE_NAME
						+ show.getMovieName() + MOVIE_YEAR + show.getMovieYear() + MOVIE_LANGUAGE
						+ show.getMovieLanguage() + CATEGORY + show.getMovieCategory() + MEMBERSHIP
						+ show.getMembership() + MOVIE_GRADE + show.getMovieGrade());
				searchDone = true;

			}
		}
		return searchDone;
	}

	/**
	 * This method is used to display all movies
	 * 
	 * @param filmCategory
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static boolean displayAllMovies(String filmCategory) throws Exception {
		List<Show> primeMovieList = ShowListDao.getShowDetails();
		boolean searchDone = false;
		for (Show show : primeMovieList) {
			if (show.getMovieCategory().equalsIgnoreCase(filmCategory.trim())) {
				System.out.println(CATEGORY + filmCategory);
				System.out.println(MOVIE_ID + show.getId() + MOVIE_GENRE + show.getMovieGenre() + MOVIE_NAME
						+ show.getMovieName() + MOVIE_YEAR + show.getMovieYear() + MOVIE_LANGUAGE
						+ show.getMovieLanguage() + CATEGORY + show.getMovieCategory() + MEMBERSHIP
						+ show.getMembership() + MOVIE_GRADE + show.getMovieGrade());
				searchDone = true;

			}
		}
		return searchDone;
	}
}
