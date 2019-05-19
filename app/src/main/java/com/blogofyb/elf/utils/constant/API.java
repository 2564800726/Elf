package com.blogofyb.elf.utils.constant;

public class API {

    /**
     * 获取更新
     */
    private static final String BASE_URL = "http://elf.egos.hosigus.com";

    /**
     * 获取更新
     */
    public static final String UPDATE = BASE_URL + "/getLastVersion.php";

    /**
     * 获取心情歌单
     */
    public static final String MOOD_SONG_LIST = BASE_URL + "/getSongListID.php";

    /**
     * 获取推荐歌单
     */
    public static final String RECOMMEND_SONG_LIST = BASE_URL + "/getRecommendID.php";

    /**
     * 获取音乐的详细信息
     */
    private static final String SONG_INFORMATION_BASE_RUL = "http://elf.egos.hosigus.com/music";

    /**
     * 获取歌单的详细信息
     */
    public static final String SONG_INFORMATION_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 获取歌单的详细信息
     */
    public static final String SONG_LIST_INFORMATION = SONG_INFORMATION_BASE_RUL + "/playlist/detail";

    /**
     * 获取mp3文件地址
     * @param songId  歌曲id
     * @return  mp3文件的地址
     */
    public static String getMp3Address(String songId) {
        return "http://music.163.com/song/media/outer/url?id=" + songId + ".mp3";
    }

    /**
     * 获取歌词
     */
    public static final String SONG_LYRIC = SONG_INFORMATION_BASE_RUL + "/lyric";
}
