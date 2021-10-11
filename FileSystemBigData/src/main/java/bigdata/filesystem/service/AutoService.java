package bigdata.filesystem.service;

import bigdata.filesystem.dto.AutoCreateDTO;
import bigdata.filesystem.dto.AutoDeleteDTO;
import bigdata.filesystem.dto.AutoUpdateDTO;
import bigdata.filesystem.dto.query.GetAutosDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AutoService {
    void createAuto(AutoCreateDTO autoCreateDTO);

    void updateAuto(AutoUpdateDTO autoUpdateDTO);

    void deleteAuto(AutoDeleteDTO autoDeleteDTO);

    Object getAutos(GetAutosDTO getAutosDTO);

    void excel2json(MultipartFile file);
}
