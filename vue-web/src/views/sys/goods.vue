<template>
    <div style="margin: 20px;">
        <div>
            <Row style="margin-bottom: 25px;">
                <Col span="8">商品编号：
                    <Input v-model="goodsNumber" placeholder="请输入..." style="width:200px"></Input>
                </Col>
                <Col span="8"><Button type="primary" shape="circle" icon="ios-search" @click="search()">搜索</Button></Col>
            </Row>
        </div>
        <div>
            <ul>
                <li>
                    <div style="padding: 10px 0;">
                        <Table border :columns="columns1" :data="data1" :height="600" @on-selection-change="s=>{change(s)}" @on-row-dblclick="s=>{dblclick(s)}"></Table>
                    </div>
                </li>
                <li>
                    <div style="text-align: right;">
                        <Page :total="total" :page-size="pageInfo.pageSize" show-elevator show-total @on-change="e=>{pageSearch(e)}"></Page>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return {
                /*用于查找的商品编号*/
                goodsNumber:null,
                /*分页total属性绑定值*/
                total:0,
                /*loading*/
                loading: true,
                /*pageInfo实体*/
                pageInfo:{
                    page:0,
                    pageSize:10
                },
                /*product实体*/
                product:{
                    id:null,
                    number:null,
                    name:null
                },
                columns1: [
                    {
                        title: '商品编号',
                        key: 'number'
                    },
                    {
                        title: '商品名称',
                        key: 'name'
                    },
                    {
                        title:'国条码',
                        key:'barCode'
                    },
                    {
                        title:'分类一',
                        key:'categoryOneName'
                    },
                    {
                        title:'分类二',
                        key:'categoryTwoName'
                    },
                    {
                        title:'分类三',
                        key:'categoryThreeName'
                    },
                    {
                        title:'优选价',
                        key:'price',
                        render: (h,params) => {
                            return h('div',params.row.price/100);
                        }
                    },
                    {
                        title:'原价',
                        key:'costPrice',
                        render: (h,params) => {
                            return h('div',params.row.costPrice/100);
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.show(params.row);
                                        }
                                    }
                                }, '显示订单详情')
                            ]);
                        }
                    }
                ],
                data1:[],
            }
        },
        mounted(){
            /*页面初始化调用方法*/
            this.getTable({
                "pageInfo":this.pageInfo,
                "loginName":this.loginName
            });
        },
        methods:{
            /*pageInfo实体初始化*/
            initPageInfo(){
                this.pageInfo.page = 0;
                this.pageInfo.pageSize = 10;
            },
            /*得到表数据*/
            getTable(e) {
                this.axios({
                    method: 'get',
                    url: '/product/all',
                    params: {
                        'page':e.pageInfo.page,
                        'pageSize':e.pageInfo.pageSize,
                        'number':e.number
                    }
                }).then(function (response) {
                    this.data1=response.data.data;
                    this.total=response.data.totalCount;
                }.bind(this)).catch(function (error) {
                    alert(error);
                });
            },
            /*搜索按钮点击事件*/
            search(){
                this.initPageInfo();
                this.getTable({
                    "pageInfo":this.pageInfo,
                    "loginName":this.loginName
                });
            },
            /*分页点击事件*/
            pageSearch(e){
                this.pageInfo.page = e-1;
                this.getTable({
                    "pageInfo":this.pageInfo,
                    "loginName":this.loginName
                });
            },
            show(row){
                alert(row.productId);
            }
        }
    }
</script>