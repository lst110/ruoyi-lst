<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图书名称" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入图书名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ISBN" prop="isbn">
        <el-input
          v-model="queryParams.isbn"
          placeholder="请输入ISBN"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="publishing">
        <el-input
          v-model="queryParams.publishing"
          placeholder="请输入出版社"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版次" prop="edition">
        <el-input
          v-model="queryParams.edition"
          placeholder="请输入版次"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版时间" prop="publishTime">
        <el-date-picker clearable
          v-model="queryParams.publishTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出版时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="CIP" prop="cip">
        <el-input
          v-model="queryParams.cip"
          placeholder="请输入CIP"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版数量" prop="publishNumber">
        <el-input
          v-model="queryParams.publishNumber"
          placeholder="请输入出版数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['wx:book:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleImport"
                v-hasPermi="['wx:book:add']"
              >导入</el-button>
            </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wx:book:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wx:book:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wx:book:export']"
        >导出图书信息</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="图书id" align="center" prop="id" />
      <el-table-column label="图书名称" align="center" prop="bookName" />
      <el-table-column label="作者" align="center" prop="author" />
      <el-table-column label="ISBN" align="center" prop="isbn" />
      <el-table-column label="出版社" align="center" prop="publishing" />
      <el-table-column label="版次" align="center" prop="edition" />
      <el-table-column label="出版时间" align="center" prop="publishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="CIP" align="center" prop="cip" />
      <el-table-column label="出版数量" align="center" prop="publishNumber" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleExportQr(scope.row)"
            v-hasPermi="['wx:book:export']"
          >下载防伪码</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wx:book:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wx:book:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改图书信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图书名称" prop="bookName">
          <el-input v-model="form.bookName" placeholder="请输入图书名称" />
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="form.isbn" placeholder="请输入ISBN" />
        </el-form-item>
        <el-form-item label="出版社" prop="publishing">
          <el-input v-model="form.publishing" placeholder="请输入出版社" />
        </el-form-item>
        <el-form-item label="版次" prop="edition">
          <el-input v-model="form.edition" placeholder="请输入版次" />
        </el-form-item>
        <el-form-item label="出版时间" prop="publishTime">
          <el-date-picker clearable
            v-model="form.publishTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出版时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="CIP" prop="cip">
          <el-input v-model="form.cip" placeholder="请输入CIP" />
        </el-form-item>
        <el-form-item label="出版数量" prop="publishNumber">
          <el-input v-model="form.publishNumber" placeholder="请输入出版数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  <!-- 图书导入对话框 -->
  <el-dialog :title="upload.title"  :visible.sync="upload.open" width="400px">
    <el-upload
  	ref="upload"
  	:limit="1"
  	accept=".xlsx, .xls"
  	:headers="upload.headers"
  	:action="upload.url + '?updateSupport=' + upload.updateSupport"
  	:disabled="upload.isUploading"
  	:on-progress="handleFileUploadProgress"
  	:on-success="handleFileSuccess"
  	:auto-upload="false"
  	drag
    >
  	<i class="el-icon-upload"></i>
  	<div class="el-upload__text">
  	  将文件拖到此处，或
  	  <em>点击上传</em>
  	</div>
  	<div class="el-upload__tip" slot="tip">
  	  <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
  	  <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
  	</div>
  	<div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
    </el-upload>
    <div slot="footer" class="dialog-footer">
  	<el-button type="primary" @click="submitFileForm">确 定</el-button>
  	<el-button @click="upload.open = false">取 消</el-button>
    </div>
  </el-dialog>


  </div>
</template>

<script>
import { listBook, getBook, delBook, addBook, updateBook } from "@/api/wx/book";
import { getToken } from "@/utils/auth";

export default {
  name: "Book",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 图书信息管理表格数据
      bookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bookName: null,
        author: null,
        isbn: null,
        publishing: null,
        edition: null,
        publishTime: null,
        cip: null,
        publishNumber: null
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（图书导入）
        open: false,
        // 弹出层标题（图书导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/wx/book/import_by_excel",
        headers: {
          Authorization: "Bearer " + getToken(),
        },
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图书信息管理列表 */
    getList() {
      this.loading = true;
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        bookName: null,
        author: null,
        isbn: null,
        publishing: null,
        edition: null,
        publishTime: null,
        cip: null,
        publishNumber: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图书信息管理";
    },

/** 导入按钮操作 */
handleImport() {
  this.upload.title = "图书导入";
  this.upload.open = true;
},
    /** 下载模板操作 */
    importTemplate() {
      this.download('wx/book/importTemplate', {
      }, `book_template_${new Date().getTime()}.xlsx`,{},'get')
    },
// 文件上传中处理
handleFileUploadProgress(event, file, fileList) {
  this.upload.isUploading = true;
},
// 文件上传成功处理
handleFileSuccess(response, file, fileList) {
  this.upload.open = false;
  this.upload.isUploading = false;
  this.$refs.upload.clearFiles();
  this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
  this.getList();
},
// 提交上传文件
submitFileForm() {
  this.$refs.upload.submit();
},


    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBook(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图书信息管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBook(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除图书信息管理编号为"' + ids + '"的数据项？').then(function() {
        return delBook(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wx/book/export', {
        ...this.queryParams
      }, `book_${new Date().getTime()}.xlsx`)
    },
      /** 生成防伪码zip按钮操作 */
      handleExportQr(row) {
        this.download('wx/book/export_qrcode/' + row.id, {
          ...this.queryParams
        }, `book_${new Date().getTime()}.zip`,{},'get')
      }
  }
};
</script>
