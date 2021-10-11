package bigdata.filesystem.comn.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ContentTypeUtils {

    private static Map<String, String> contentTypeMap = new HashMap<String, String>() {
        {
            put("BMP", "image/bmp");
            put("GIF", "image/gif");
            put("JPEG", "image/jpeg");
            put("PNG", "image/png");
            put("SVG", "image/svg+xml");
            put("XLS", "application/vnd.ms-excel");
            put("XLSX", "pplication/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            put("DOC", "application/msword");
            put("DOCX", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            put("PPT", "application/vnd.ms-powerpoint");
            put("PPTX", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
            put("PDF", "application/pdf");
            put("MP4", "video/mp4");
            put("DEFAULT", "text/plain");
        }
    };

    public static String getContentType(String fileType) {
        String contentType = contentTypeMap.get(StringUtils.upperCase(fileType));
        if (StringUtils.isNotBlank(contentType)) {
            return contentType;
        }
        return ContentTypeConstant.DEFAULT;
    }
}
