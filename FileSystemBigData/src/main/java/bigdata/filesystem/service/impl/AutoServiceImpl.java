package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.base.BaseService;
import bigdata.filesystem.comn.utils.TokenUtil;
import bigdata.filesystem.dto.AutoCreateDTO;
import bigdata.filesystem.dto.AutoDeleteDTO;
import bigdata.filesystem.dto.AutoUpdateDTO;
import bigdata.filesystem.dto.query.GetAutosDTO;
import bigdata.filesystem.dto.query.GetAutosResultDTO;
import bigdata.filesystem.entity.Auto;
import bigdata.filesystem.mapper.AutoMapper;
import bigdata.filesystem.service.AutoService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class AutoServiceImpl extends BaseService<Auto, Long> implements AutoService {

    @Autowired
    private AutoMapper autoMapper;

    @Override
    public void createAuto(AutoCreateDTO autoCreateDTO) {
        // 判断表名是否已经存在
        List<Auto> autos = autoMapper.getAutosByTableName(autoCreateDTO.getTableName());
        if (null != autos && autos.size() != 0) {
            this.throwException(BaseMessage.AUTO_TABLE_NAME_EXISTED.getMsg());
        }
        Auto auto = new Auto();
        BeanUtils.copyProperties(autoCreateDTO, auto);
        Date now = new Date();
        auto.setCreateTime(now);
        this.save(auto);
    }

    @Override
    public void updateAuto(AutoUpdateDTO autoUpdateDTO) {
        // 判断表名是否存在
        Auto auto = this.getById(autoUpdateDTO.getAutoId());
        if (null == auto) {
            this.throwException(BaseMessage.AUTO_NOT_EXISTED.getMsg());
        }
        // 判断是否修改表名
        if (!StringUtils.equalsIgnoreCase(auto.getTableName(), autoUpdateDTO.getTableName())) {
            // 判断表名是否已经存在
            List<Auto> autos = autoMapper.getAutosByTableName(autoUpdateDTO.getTableName());
            if (null != autos && autos.size() != 0) {
                this.throwException(BaseMessage.AUTO_TABLE_NAME_EXISTED.getMsg());
            }
        }
        Date now = new Date();
        BeanUtils.copyProperties(autoUpdateDTO, auto);
        auto.setUpdateTime(now);
        this.update(auto);
    }

    @Override
    public void deleteAuto(AutoDeleteDTO autoDeleteDTO) {
        this.deleteById(autoDeleteDTO.getAutoId());
    }

    @Override
    public Object getAutos(GetAutosDTO getAutosDTO) {
        int pageSize = getAutosDTO.getPageSize();
        startPage(getAutosDTO.getPageNum(), pageSize);

        List<GetAutosResultDTO> list = autoMapper.getAutos(getAutosDTO);

        PageInfo<GetAutosResultDTO> result = new PageInfo<>(list);
        return pageSize == 0 ? result.getList() : result;
    }

    @Override
    public void excel2json(MultipartFile file) {
        // 获取创建用户
        String userNum = TokenUtil.getUserNum();
        Date now = new Date();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file.getInputStream());
        } catch (Exception e) {
            this.throwException(e, BaseMessage.EXCEL_PARSE_ERROR.getMsg());
        }
        //获取sheet数
        int sheetNum = workbook.getNumberOfSheets();
        for (int s = 0; s < sheetNum; s++) {
            // Get the Sheet of s.
            Sheet sheet = workbook.getSheetAt(s);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            if (rownum <= 1) {
                continue;
            }
            //获取第一行
            Row row1 = sheet.getRow(0);
            //获取最大列数
            int colnum = row1.getPhysicalNumberOfCells();
            JSONArray jsonArray = new JSONArray();
            for (int i = 1; i < rownum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    JSONObject rowObj = new JSONObject();
                    try {
                        //循环列
                        for (int j = 0; j < colnum; j++) {
                            Cell cellData = row.getCell(j);
                            if (cellData != null) {
                                //判断cell类型
                                switch (cellData.getCellType()) {
//                                    case Cell.CELL_TYPE_NUMERIC: {
                                    case NUMERIC: {
                                        rowObj.put(row1.getCell(j).getStringCellValue(), cellData.getNumericCellValue());
                                        break;
                                    }
//                                    case Cell.CELL_TYPE_FORMULA: {
                                    case FORMULA: {
                                        //判断cell是否为日期格式
                                        if (DateUtil.isCellDateFormatted(cellData)) {
                                            //转换为日期格式YYYY-mm-dd
                                            rowObj.put(row1.getCell(j).getStringCellValue(), cellData.getDateCellValue());
                                        } else {
                                            //数字
                                            rowObj.put(row1.getCell(j).getStringCellValue(), cellData.getNumericCellValue());
                                        }
                                        break;
                                    }
//                                    case Cell.CELL_TYPE_STRING: {
                                    case STRING: {
                                        rowObj.put(row1.getCell(j).getStringCellValue(), cellData.getStringCellValue());
                                        break;
                                    }
                                    default:
                                        rowObj.put(row1.getCell(j).getStringCellValue(), "");
                                }
                            } else {
                                rowObj.put(row1.getCell(j).getStringCellValue(), "");

                            }
                        }
                    } catch (Exception e) {
                        this.throwException(e, BaseMessage.EXCEL_PARSE_ERROR.getMsg());
                    }
                    if (!rowObj.isEmpty()) {
                        jsonArray.add(rowObj);
                    }
                }
            }
            if (!jsonArray.isEmpty()) {
                // 保存Json数据到数据库
                List<Auto> autos = autoMapper.getAutosByTableName(sheet.getSheetName());
                if (null != autos && autos.size() != 0) {
                    this.throwException(BaseMessage.AUTO_TABLE_NAME_EXISTED.getMsg());
                }
                String content = jsonArray.toJSONString();
                Auto auto = new Auto();
                auto.setCreateTime(now);
                auto.setCreateUser(userNum);
                auto.setContent(content);
                auto.setTableName(sheet.getSheetName());
                this.save(auto);
            }
        }
    }
}
