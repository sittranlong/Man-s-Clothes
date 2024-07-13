<template>
    <div>
        <header-component/>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <br><br><br>
                    <h1>Chi tiết đơn hàng</h1>
                </div>
            </div>
            <div>
                <v-card>
                    <v-card-title>
                        <h3>Thông tin đơn hàng</h3>
                    </v-card-title>
                    <v-card-text>
                        <div class="row">
                            <div class="col-md-6">
                                <p><b>Họ tên:</b> {{ order?.name }}</p>
                                <p><b>Địa chỉ:</b> {{ order?.address }}</p>
                                <p><b>Số điện thoại:</b> {{ order?.phone }}</p>
                            </div>
                            <div class="col-md-6">
                                <p><b>Ngày đặt hàng:</b> {{ new Date(order?.createDate) }}</p>
                            </div>
                        </div>
                    </v-card-text>
                </v-card>
            </div>
            <div class="mb-lg-4 my-3">
                <table class="table table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên sản phẩm</th>
                        <th>Số lượng</th>
                        <th>Thành tiền</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, index) in order?.orderDetails" :key="index">
                        <td>
                            {{ index + 1 }}
                        </td>
                        <td>
                            {{ item?.productDetail?.product?.name }}
                        </td>
                        <td>
                            {{ item?.quantity }}
                        </td>
                        <td>
                            {{ formatMoney(Number(item?.price * item?.quantity)) }}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="text-start">Phí vận chuyển</td>
                        <td>{{ formatMoney(Number(order?.shippingFee)) }}</td>
                    </tr>
                    <tr>
                        <td colspan="3" class="text-start">Tổng tiền</td>
                        <td>{{ formatMoney(Number(order?.finalTotal)) }}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <footer-component/>
    </div>
</template>

<script lang="ts">
import {defineComponent, inject} from "vue";
import {OrderService} from "@/base/service/order.service";
import {Order} from "@/base/model/order.model";
import {Product} from "@/base/model/product.model";
import {useRoute, useRouter} from "vue-router";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import formatMoney from "@/plugins/utils";

export default defineComponent({
    name: "OrderDetailComponent",
    components: {FooterComponent, HeaderComponent},
    setup() {
        return {
            router: useRouter(),
            route: useRoute(),
            orderService: inject('orderService') as OrderService,
        }
    },
    data() {
        return {
            order : {} as Order,
            id: this.route.params.id as string,
            Order: Order,
            Product: Product
        }
    },
    methods: {
        initOrder() {
            this.orderService.getOrder(this.id).then(res => {
                this.order = res;
            }).catch(err => {
                console.log(err);
            })
        },
        formatMoney
    },
    created() {
        this.initOrder();
    }
});
</script>

<style>

</style>