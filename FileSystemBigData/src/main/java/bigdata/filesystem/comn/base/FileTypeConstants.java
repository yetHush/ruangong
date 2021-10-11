package bigdata.filesystem.comn.base;

/**
 * @Description: 文件类型定义
 * @auther: yangqh
 * @date: 2020/12/25/025 19:45
 */
public interface FileTypeConstants {
    // 图片格式
    String JPEG = "FFD8FF";
    String PNG = "89504E47";
    String GIF = "47494638";
    String TIFF1 = "49492A00";
    String TIFF2 = "4D4D002A";
    String BMP = "424D";
    String IMAGE = "IMAGE";
    String[] IMAGES = {JPEG, PNG, GIF, TIFF1, TIFF2, BMP};
    // 压缩包类型
    String ZIP = "504B0304";
    String RAR = "52617221";
    String GZ_TGZ = "1F8B08";
    String ARJ = "60EA";
    String ARCHIVE = "ARCHIVE";
    String[] ARCHIVES = {ZIP, RAR, GZ_TGZ, ARJ};
    // OFFICE类型
    String XLS = "D0CF11E0";
    String DOC = "D0CF11E0";
    String PPT = "D0CF11E0";
    String PPTX = "504B0304";
    String DOCX = "504B0304";
    String XLSX = "504B0304";
    /**
     * WPS文字wps、表格et、演示dps都是一样的
     */
    String WPS = "D0CF11E0A1B11AE10000";

    // PDF
    String PDF = "255044462D312E";
    String OFFICE = "OFFICE";
    String[] OFFICES = {XLS, DOC, PPT, PPTX, DOCX, XLSX, WPS, PDF};
    // 视频类型
    String MPEG1 = "000001BA";
    String MPEG2 = "000001B3";
    String WAV = "57415645";
    String AVI = "41564920";
    String RAM = "2E7261FD"; //Real Audio (ram)
    String RM = "2E524D46"; //Real Media (rm)
    String MOV = "6D6F6F76";
    String ASF = "3026B2758E66CF11";
    String MIDI = "4D546864";
    /**
     * MP4
     */
    String MP4 = "00000020667479706D70";
    /**
     * MP3
     */
    String MP3 = "49443303000000002176";
    /**
     * FLV
     */
    String FLV = "464C5601050000000900";
    String VIDEO = "VIDEO";
    String[] VIDEOS = {MPEG1, MPEG2, WAV, AVI, RAM, RM, MOV, ASF, MIDI, MP4, MP3, FLV};


}
