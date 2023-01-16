/*
This file allows us to create a SongList object that can be used to easily output a
random song from a user-input-given location contained in the given file as well as
open a youtube link to the song's video.
 */

import javax.print.attribute.URISyntax;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SongList {
    // instance variables
    // list of songs (values) associated to each country (key)
    private Map<String, String[]> songList;
    // list of youtube links (values) associated to each song (key)
    private Map<String, String> linkList;

    // parses the given file to create a SongList, using two HashMaps to associate
    // a country with all of its songs and a youtube link to each song
    public SongList(String file) throws FileNotFoundException {
        songList = new HashMap<>();
        linkList = new HashMap<>();
        Scanner sc = new Scanner(new FileReader(file));
        while (sc.hasNextLine()) {
            // st tokenizes the line containing the number of songs and location
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int size = Integer.parseInt(st.nextToken()); // number of songs in location
            StringBuilder location = new StringBuilder(st.nextToken());
            while (st.hasMoreTokens()) {
                location.append(" ");
                location.append(st.nextToken()); // adds spaces to location name as needed
            }
            String[] arr = new String[size];
            for (int i = 0; i < size; i++) {
                String song = sc.nextLine();
                arr[i] = song;
                // adds youtube link to each corresponding song
                linkList.put(song, sc.nextLine());
            }
            // adds song array to each corresponding location
            songList.put(location.toString(), arr);
        }
    }

    // returns the String at a random index of the given array
    private String random(String[] arr) {
        int index = (int) (Math.random() * arr.length);
        return arr[index];
    }

    // returns a new String with the first letter of the given String capitalized
    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    // prints a random song from the given location (as long as it is in the file)
    // and opens a pop-up window to the youtube video of the song
    public void outputSong(String input) throws URISyntaxException, IOException {
        input = input.toLowerCase();
        if (songList.containsKey(input)) {
            String song = random(songList.get(input));
            // capitalizes the first letter of each word in the location name
            StringTokenizer st = new StringTokenizer(input);
            StringBuilder locationName = new StringBuilder(capitalize(st.nextToken()));
            while (st.hasMoreTokens()) {
                locationName.append(" ");
                locationName.append(capitalize(st.nextToken()));
            }

            // prints song and opens pop-up to youtube video
            System.out.println(
                    "Your song from " + locationName.toString() + " is: " + song);
            java.awt.Desktop.getDesktop().browse(new URI(linkList.get(song)));
        }
        else {
            System.out.println("Location not found");
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        SongList list = new SongList("songs.txt");
        // test 1: check if outputSong() outputs songs from the correct location
        for (int i = 0; i < 3; i++) {
            list.outputSong("korea");
            // manually check that all three songs are from Korea
        }
        // test 2: check how randomized outputSong() is
        for (int i = 0; i < 50; i++) {
            list.outputSong("china");
            // since china has 10 songs, manually check that each song appears ~5 times
        }

        // test 3: songList in constructor correctly matches links to songs
        System.out.println(list.linkList.get("Sisa Rasa by Mahalini"));
        // check that the output matches this link: https://www.youtube.com/watch?v=Wh66ThpxvI4

        // test 4: songList in constructor correctly matches song array to location
        String[] ghanaSongs = list.songList.get("ghana");
        for (int i = 0; i < ghanaSongs.length; i++) {
            System.out.println(ghanaSongs[i]);
        }
        // check that the output matches the song list:
        /*
        Jaara (ft. Medikal) by Sarkodie
        Paa Kwesi (ft. RJZ) by Joey B
        Balance (ft. Sarkodie) by MzVee
         */

    }
}
