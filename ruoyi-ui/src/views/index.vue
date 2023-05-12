<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>一码溯源图书版权认证查询</h2>
        <p>
        现阶段，电商平台的迅速崛起使得人们的购物方式发生了巨大改变，图书也不例外。图书市场也转变为由线上和线下相结合的方式，在给消费者带来便利的同时也给图书盗版商可趁之机。由于电商平台的多样，电商平台注册商家的鱼龙混杂，使得盗版图书无孔不入，消费者难以鉴别盗版图书，更无法合理维权。
        现存的传统盗版图书鉴别方式步骤繁杂、准确率低、需要消费者对正版图书有充足了解，无法满足高效鉴别盗版图书的需求。而市面上现存的其他盗版图书鉴别方式存在着成本过高，鉴别结果易被复制失真等问题。与这些鉴别方式相比，本系统鉴别查询方式简单高效、成本较低可行性强、系统功能完善，具备防伪查询、异常预警、维权追责等功能。本系统采用微信小程序与后台管理系统结合的方式。微信小程序实现消费者扫码获取图书版权信息功能，版权所有者版权认证，二维码批量申请的功能，盗版预警提示功能。每一个图书版权二维码都可以查询获得其图书的版权信息、销售印刷渠道、被扫码次数及每次扫码的mac地址。当扫码次数大于1次给予消费者盗版警告，当图书扫码次数大于版权方规定次数，给予版权用户被盗版印刷警告。对于获得版权认证的用户的批量二维码申请自动生成二维码并将二维码打包返回用户。后台管理系统对于二维码扫码信息进行实时监控，对于超过规定范围的信息及时返回用户版权侵权警告。使版权查询操作更加高效便捷，版权保护全方位、多角度、高效可行。从版权认证到版权维护到侵权警告到侵权追责全方位、多角度保障版权。
        本系统采用了微信小程序+后台管理系统的前后端结合的方式开发而成，借助Mybatis等框架辅助建立起数据库，并通过MySQL数据库+Redis数据库进行数据的管理统计。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>
        <p>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-cloudy"
            plain
            @click="goTarget('https://gitee.com/y_project/RuoYi-Vue')"
            >访问码云</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-s-home"
            plain
            @click="goTarget('http://ruoyi.vip')"
            >访问主页</el-button
          >
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">

    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.5"
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

