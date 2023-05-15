package com.ruoyi.wx.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import com.ruoyi.wx.domain.WxUsers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.wx.domain.WxBook;
import com.ruoyi.wx.domain.WxCode;
import com.ruoyi.wx.service.IWxBookService;
import com.ruoyi.wx.service.IWxCodeService;
import com.ruoyi.wx.service.IWxUsersService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
/**
 * 图书信息管理Controller
 * 
 * @author lst
 * @date 2023-05-12
 */
@RestController
@RequestMapping("/wx/book")
public class WxBookController extends BaseController
{
    @Autowired
    private IWxBookService wxBookService;
    @Autowired
    private IWxCodeService wxCodeService;
    @Autowired
    private IWxUsersService wxUsersService;
    /**
     * 查询图书信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(WxBook wxBook)
    {
        startPage();
        List<WxBook> list = wxBookService.selectWxBookList(wxBook);
        return getDataTable(list);
    }

    /**
     * 导出图书信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('wx:book:export')")
    @Log(title = "图书信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WxBook wxBook)
    {
        List<WxBook> list = wxBookService.selectWxBookList(wxBook);
        ExcelUtil<WxBook> util = new ExcelUtil<WxBook>(WxBook.class);
        util.exportExcel(response, list, "图书信息管理数据");
    }

    /**
     * 获取图书信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('wx:book:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(wxBookService.selectWxBookById(id));
    }

    /**
     * 导入图书信息管理
     */
    @PreAuthorize("@ss.hasPermi('wx:book:add')")
    @Log(title = "图书信息管理", businessType = BusinessType.INSERT)
    @PostMapping("/import_by_excel")
    public AjaxResult addByExcel(MultipartFile file) throws Exception
    {
        ExcelUtil<WxBook> util = new ExcelUtil<WxBook>(WxBook.class);
        List<WxBook> bookList = util.importExcel(file.getInputStream());
        int successCounter = 0;
        int failedCounter = 0;
        for (WxBook wxBook : bookList) {
            try {
                wxBookService.insertWxBook(wxBook);
                successCounter++;
            } catch (Exception e) {
                e.printStackTrace();
                failedCounter++;
                continue;
            }
        }
        return success("插入成功：" + successCounter + ":条，插入失败：" + failedCounter + "条");
        // return toAjax(wxBookService.insertWxBook(wxBook));
    }
    /**
     * 导入模板
     */
    // @PreAuthorize("@ss.hasPermi('wx:book:export')")
    @Anonymous
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<WxBook> util = new ExcelUtil<WxBook>(WxBook.class);
//        return util.exportExcel(new ArrayList<WxBook>(),"sheet1");
        util.exportExcel(response,new ArrayList<WxBook>(),"sheet1");
    }

//    @PreAuthorize("@ss.hasPermi('wx:book:add')")
    @Log(title = "图书信息管理", businessType = BusinessType.INSERT)
    @PostMapping("/import_by_excel_public")
    @Anonymous
    public AjaxResult addByExcelPublic(MultipartFile file,String openid) throws Exception
    {
        WxUsers wxUser =  wxUsersService.selectWxUsersByOpenId(openid);
        if(wxUser == null){
            return error("用户不存在");
        }
        ExcelUtil<WxBook> util = new ExcelUtil<WxBook>(WxBook.class);
        List<WxBook> bookList = util.importExcel(file.getInputStream());
        int successCounter = 0;
        int failedCounter = 0;
        for (WxBook wxBook : bookList) {
            try {
                wxBookService.insertWxBook(wxBook);
                for (int i = 0; i < wxBook.getPublishNumber(); i++) {
                    WxCode wxCode = new WxCode();
                    wxCode.setBook_id(String.valueOf(wxBook.getId()));
                    wxCode.setCodeStatus("1");
                    wxCode.setCreateUser(String.valueOf(wxUser.getId()));
                    wxCode.setCreateTime(new Date());
                    wxCode.setRemark(UUID.randomUUID().toString());
                    wxCodeService.insertWxCode(wxCode);
                }
                successCounter++;
            } catch (Exception e) {
                e.printStackTrace();
                failedCounter++;
                continue;
            }
        }
        return success("插入成功：" + successCounter + ":条，插入失败：" + failedCounter + "条");
        // return toAjax(wxBookService.insertWxBook(wxBook));
    }


    /**
     * 新增图书信息管理
     */
    @PreAuthorize("@ss.hasPermi('wx:book:add')")
    @Log(title = "图书信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WxBook wxBook)
    {
        return toAjax(wxBookService.insertWxBook(wxBook));
    }

    /**
     * 修改图书信息管理
     */
    @PreAuthorize("@ss.hasPermi('wx:book:edit')")
    @Log(title = "图书信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WxBook wxBook)
    {
        return toAjax(wxBookService.updateWxBook(wxBook));
    }

    /**
     * 删除图书信息管理
     */
    @PreAuthorize("@ss.hasPermi('wx:book:remove')")
    @Log(title = "图书信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(wxBookService.deleteWxBookByIds(ids));
    }


    /**
     * 导出图书二维码
     * @throws IOException
     */
    @PreAuthorize("@ss.hasPermi('wx:book:export')")
    @Log(title = "图书信息管理", businessType = BusinessType.DELETE)
	@GetMapping("/export_qrcode/{id}")
    public void exportQrcode(HttpServletResponse httpServletResponse,@PathVariable String id) throws IOException
    {
        // 先校验图书是否存在
        WxBook book =  wxBookService.selectWxBookById(id);
        if(book == null) {
            System.out.println("图书ID：" + id + "不存在!");
            return;
        }
        WxCode book_code = new WxCode();
        book_code.setBook_id(id);
        List<WxCode> qrcodeList = wxCodeService.selectWxCodeList(book_code);
        List<String> qrList = new ArrayList<String>();
        WxCode wxCodeTmp = new WxCode();
        wxCodeTmp.setBook_id(id);
        wxCodeTmp.setCreateTime(new Date());
        Long qrNum = book.getPublishNumber();
        Long currentNum = listIsNull(qrcodeList) ? 0L : qrcodeList.size();
        // 将remark中填入uuid，作为二维码内容
        if(currentNum < qrNum) {
            // 如果还没有生成二维码内容或者生成数量不够的情况下，则先追加/创建记录
            for (int i = 0; i < qrNum - currentNum; i++) {
                String qrContent = UUID.randomUUID().toString();
                try {
                    wxCodeTmp.setRemark(qrContent); 
                    wxCodeService.insertWxCode(wxCodeTmp);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                } 
                qrList.add(qrContent);
            }
        } else {
            // 如果已发布
            for (WxCode qr : qrcodeList) {
                qrList.add(qr.getRemark());
            }
        }
        
        // 指明response的返回对象是文件流
        httpServletResponse.setHeader("Content-Type", "application/zip");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID() + ".zip");
        // List<BitMatrix> zxingBitMatrixs = new ArrayList<BitMatrix>();
        // 创建一个字节输出流
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(byteOutputStream);
        //压缩包文件名称
        // String downloadFilename = "图书：" + book.getBookName() + "防伪码";
        // headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 设置在下载框默认显示的文件名
        // headers.set("Content-disposition", "attachment; filename=" + downloadFilename.concat(".zip"));
        // httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + downloadFilename.concat(".zip"));
        //设置图片的文字编码以及内边框
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //边框距
        hints.put(EncodeHintType.MARGIN, 0);
        String format = "png";// 图像类型
        // BitMatrix bitMatrix;
        for (String content : qrList) {
            try {
                //参数分别为：编码内容、编码类型、图片宽度、图片高度，设置参数
                BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);
                // BufferedImage buffImg = MatrixToImageWriter.toBufferedImage(bitMatrix);

                ZipEntry entry = new ZipEntry(content + "." + format);
                zos.putNextEntry(entry);
                
                // ImageIO.write(buffImg, format, zos);
                // zos.flush();
                MatrixToImageWriter.writeToStream(bitMatrix, format, zos);
                zos.closeEntry();
                zos.flush();
                bitMatrix.clear();
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("-------------------------" + content);
                continue;
            }
            // bitMatrix.writeToStream(bitMatrix, format, zos);
        }
        zos.finish();
        // 释放资源
        zos.close();
        byte[] compressedData = byteOutputStream.toByteArray();
        httpServletResponse.getOutputStream().write(compressedData);
        return;
    }

    private <T> boolean listIsNull(List<T> l) {
        if(l == null) {
            return true;
        }
        if(l.size() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 导出图书二维码
     * @throws IOException
     */
    @Log(title = "图书信息管理", businessType = BusinessType.DELETE)
	@GetMapping("/export_qrcode_all/{openid}")
    public void exportQrcodeAll(@PathVariable String openid,HttpServletResponse httpServletResponse) throws IOException
    {
        WxUsers wxUser = wxUsersService.selectWxUsersByOpenId(openid);
        if(wxUser == null) {
            return;
        }
        
        WxCode wxCode = new WxCode();
        wxCode.setCreateUser(String.valueOf(wxUser.getId()));
        List<WxCode> qrcodeList = wxCodeService.selectWxCodeList(wxCode);
        List<String> qrList = new ArrayList<String>();
        for (WxCode qr : qrcodeList) {
            qrList.add(qr.getRemark());
        }
        
        // 指明response的返回对象是文件流
        httpServletResponse.setHeader("Content-Type", "application/zip");
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID() + ".zip");
        // List<BitMatrix> zxingBitMatrixs = new ArrayList<BitMatrix>();
        // 创建一个字节输出流
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(byteOutputStream);
        //压缩包文件名称
        // String downloadFilename = "图书：" + book.getBookName() + "防伪码";
        // headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 设置在下载框默认显示的文件名
        // headers.set("Content-disposition", "attachment; filename=" + downloadFilename.concat(".zip"));
        // httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + downloadFilename.concat(".zip"));
        //设置图片的文字编码以及内边框
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //边框距
        hints.put(EncodeHintType.MARGIN, 0);
        String format = "png";// 图像类型
        // BitMatrix bitMatrix;
        for (String content : qrList) {
            try {
                //参数分别为：编码内容、编码类型、图片宽度、图片高度，设置参数
                BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);
                // BufferedImage buffImg = MatrixToImageWriter.toBufferedImage(bitMatrix);
                String book_id = "";
                for (int i = 0; i < qrcodeList.size(); i++) {
                    if(qrcodeList.get(i).getRemark().equals(content)) {
                        book_id = qrcodeList.get(i).getBook_id();
                        break;
                    }
                }
                ZipEntry entry = new ZipEntry("book_" + book_id + "_" + content + "." + format);
                zos.putNextEntry(entry);
                
                // ImageIO.write(buffImg, format, zos);
                // zos.flush();
                MatrixToImageWriter.writeToStream(bitMatrix, format, zos);
                zos.closeEntry();
                zos.flush();
                bitMatrix.clear();
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("-------------------------" + content);
                continue;
            }
            // bitMatrix.writeToStream(bitMatrix, format, zos);
        }
        zos.finish();
        // 释放资源
        zos.close();
        byte[] compressedData = byteOutputStream.toByteArray();
        httpServletResponse.getOutputStream().write(compressedData);
        return;
    }
}
