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
                    <div style="text-align: right;">
                        <Page :total="total" :page-size="pageInfo.pageSize" show-elevator show-total @on-change="e=>{pageSearch(e)}"></Page>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>
<script type="text/javascript" src="../../resources/jquery-1.8.3.js"></script>
<script>
    export default {
        data(){
            return {
                /*用于查找的商品编号*/
                goodsNumber:null,
                /*分页total属性绑定值*/
                total:1,
                /*loading*/
                loading: true,
                /*pageInfo实体*/
                pageInfo:{
                    page:1,
                    pageSize:1
                },
                /*user实体*/
                user:{
                    id:null,
                    goodsNumber:null,
                    goodsName:null
                },
                columns1: [
                    {
                        title: '商品编号',
                        key: 'goodsNumber'
                    },
                    {
                        title: '商品名称',
                        key: 'goodsName'
                    }
                ],
                data:[],

            }
        },
        mounted(){
            /*页面初始化调用方法*/
            this.getTable({
                "pageInfo":this.pageInfo,
                "goodsNumber":this.goodsNumber
            });
            // this.axios({
            //     method: 'post',
            //     url: 'https://testapi.sfbest.com/open-api/open/product/getProductList?app_key=G034E1L8XW&access_token=6c568c57-58d9-4d92-9912-f5894ec7f113'
            // }).then(function (response) {
            //     alert(response.data);
            //     // this.data2Temp = response.data;
            // }.bind(this)).catch(function (error) {
            //     alert(error);
            // });
        },
        methods:{
            /*得到表数据*/
            getTable(e) {
                this.axios({
                    method: 'post',
                    url: 'https://testapi.sfbest.com/open-api/open/product/getProductList?app_key=G034E1L8XW&access_token=6c568c57-58d9-4d92-9912-f5894ec7f113',
                    params: {
                        'page':e.pageInfo.page,
                        'pageSize':e.pageInfo.pageSize,
                        'number':e.goodsNumber
                    }
                }).then(function (response) {
                    alert(response.data);
                    // this.data1=response.data.data;
                    // this.listDateSet(this.data1);
                    // this.total=response.data.totalCount;
                }.bind(this)).catch(function (error) {
                    alert(error);
                });
                // var jsonObject = {
                //             'page':e.pageInfo.page,
                //             'pageSize':e.pageInfo.pageSize,
                //             'number':e.goodsNumber
                //         }
                // $.getJSON("http://wavky.com/api/helloWorld?jsoncallback=?",jsonObject, function(data){
                //     alert(data);
                // })
            }
        }
    }
</script>