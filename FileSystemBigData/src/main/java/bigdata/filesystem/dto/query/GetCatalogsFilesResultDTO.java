package bigdata.filesystem.dto.query;

import lombok.Data;

import java.util.List;

@Data
public class GetCatalogsFilesResultDTO {
    // id
    private Long id;
    // 父目录
    private Long parentId;
    // 文件或者目录名
    private String name;
    // 类型：catalog/file
    private String type;
    // 如果是文件的话就有路径
    private String filePath;
    // 如果是文件的话就用文件名+后缀
    private String fileName;
    // 子目录及问加你
    private List<GetCatalogsFilesResultDTO> children;
}
