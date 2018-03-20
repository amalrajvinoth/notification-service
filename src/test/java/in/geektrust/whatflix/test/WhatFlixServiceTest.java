package in.geektrust.whatflix.test;

import in.geektrust.whatflix.model.Movie;
import in.geektrust.whatflix.model.UserPreference;
import in.geektrust.whatflix.repository.WhatFlixRepository;
import in.geektrust.whatflix.service.WhatFlixService;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WhatFlixServiceTest {
    @InjectMocks
    WhatFlixService service;

    @Mock
    private WhatFlixRepository whatFlixRepository;

    @Mock
    private Map<Integer, UserPreference> userPrefMap;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetById() {
        Movie movie = generateMovie("Avatar", "CCH Pounder", "Joel David Moore", "Wes Studi", "James Cameron", BigDecimal.valueOf(7.9));
        when(whatFlixRepository.findById(anyLong())).thenReturn(Optional.of(movie));

        assertThat(service.get(1L), is(Optional.of(movie)));
    }

    @Test
    public void testSearchByUserPref() {
        String userPrefLang[] = {"English", "Spanish"};
        String userPrefAct[] = {"Denzel Washington","Kate Winslet","Emma Suárez","Tom Hanks"};
        String userPrefDir[] = {"Steven Spielberg","Martin Scorsese","Pedro Almodóvar"};

        UserPreference u = new UserPreference();
        u.setUserId(100);
        u.setPreferredLanguages(userPrefLang);
        u.setFavouriteActors(userPrefAct);
        u.setFavouriteDirectors(userPrefDir);

        userPrefMap.put(100, u);

        Collection<Movie> movieList = new ArrayList<>();
        movieList.add(generateMovie("Avatar", "CCH Pounder", "Joel David Moore", "Wes Studi", "James Cameron", BigDecimal.valueOf(7.9)));

        String searchTerms[] = {"Tom Hanks"};

        List<String> result = Arrays.asList("Avatar")  ;
        when(whatFlixRepository.find(searchTerms, userPrefLang, userPrefDir, userPrefAct)).thenReturn(result);
        assertThat(service.findMovies(100, "Tom Hanks"), is(equalTo(anyCollection())));
    }

    private Movie generateMovie(String movieTitle, String actor1, String actor2, String actor3, String director, BigDecimal imdbScore) {
        Movie movie = new Movie();
        movie.setMovieTitle(movieTitle);
        movie.setActor1Name(actor1);
        movie.setActor2Name(actor2);
        movie.setActor3Name(actor3);
        movie.setDirectorName(director);
        movie.setImdbScore(imdbScore);
        return movie;
    }
}