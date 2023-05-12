<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="预警扫码次数" prop="warn_num">
        <el-input
          v-model="queryParams.warn_num"
          placeholder="请输入预警扫码次数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警状态" prop="warn_state">
        <el-select v-model="queryParams.warn_state" placeholder="请选择预警状态" clearable>
          <el-option
            v-for="dict in dict.type.wx_warn_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="发生预警时间" prop="warn_time">
        <el-date-picker clearable
          v-model="queryParams.warn_time"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发生预警时间">
        </el-date-picker>
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
          v-hasPermi="['wx:warn:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wx:warn:edit']"
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
          v-hasPermi="['wx:warn:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wx:warn:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warnList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="预警二维码id" align="center" prop="warn_qrid" />
      <el-table-column label="预警扫码次数" align="center" prop="warn_num" />
      <el-table-column label="预警扫码ip" align="center" prop="warn_ip" />
      <el-table-column label="预警状态" align="center" prop="warn_state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wx_warn_status" :value="scope.row.warn_state"/>
        </template>
      </el-table-column>
      <el-table-column label="发生预警时间" align="center" prop="warn_time" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.warn_time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="预警触发用户id" align="center" prop="warn_user" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wx:warn:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wx:warn:remove']"
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

    <!-- 添加或修改预警处理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="预警二维码id" prop="warn_qrid">
          <el-input v-model="form.warn_qrid" placeholder="请输入预警二维码id" />
        </el-form-item>
        <el-form-item label="预警扫码次数" prop="warn_num">
          <el-input v-model="form.warn_num" placeholder="请输入预警扫码次数" />
        </el-form-item>
        <el-form-item label="预警扫码ip" prop="warn_ip">
          <el-input v-model="form.warn_ip" placeholder="请输入预警扫码ip" />
        </el-form-item>
        <el-form-item label="预警状态" prop="warn_state">
          <el-radio-group v-model="form.warn_state">
            <el-radio
              v-for="dict in dict.type.wx_warn_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="发生预警时间" prop="warn_time">
          <el-date-picker clearable
            v-model="form.warn_time"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择发生预警时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预警触发用户id" prop="warn_user">
          <el-input v-model="form.warn_user" placeholder="请输入预警触发用户id" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWarn, getWarn, delWarn, addWarn, updateWarn } from "@/api/wx/warn";

export default {
  name: "Warn",
  dicts: ['wx_warn_status'],
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
      // 预警处理表格数据
      warnList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warn_num: null,
        warn_state: null,
        warn_time: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "ID不能为空", trigger: "blur" }
        ],
        warn_qrid: [
          { required: true, message: "预警二维码id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询预警处理列表 */
    getList() {
      this.loading = true;
      listWarn(this.queryParams).then(response => {
        this.warnList = response.rows;
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
        warn_qrid: null,
        warn_num: null,
        warn_ip: null,
        warn_state: null,
        warn_time: null,
        warn_user: null
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
      this.title = "添加预警处理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWarn(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改预警处理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWarn(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWarn(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除预警处理编号为"' + ids + '"的数据项？').then(function() {
        return delWarn(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wx/warn/export', {
        ...this.queryParams
      }, `warn_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
