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
                                <p><b>Ngày đặt hàng:</b> {{ formatDate(order?.createDate) }}</p>
                            </div>
                            <div class="col-md-6">
                                <p><b>Ngày giao hàng:</b> {{ formatDateWithPattern(order?.deliveryDate) }}</p>
                            </div>
                            <div class="col-md-6">
                                <p><b>Ngày nhận hàng dự kiến:</b> {{ formatDateWithPattern(order?.expectedDeliveryDate) }}</p>
                            </div>
                            <div class="col-md-6">
                                <p><b>Trạng thái:</b> <span class="badge"
                                                            :class="[
                              {'bg-warning': order?.status === Order.STATUS_PENDING}
                              ,{'bg-success': order?.status === Order.STATUS_PROCESSING
                              || order?.status === Order.STATUS_SHIPPING
                              || order?.status === Order.STATUS_COMPLETED},
                              {'bg-danger': order?.status === Order.STATUS_CANCELLED},{'bg-danger' : order?.status === Order.STATUS_REFUNDED }]">
						{{ OrderStatusText(order?.status) }}
					</span></p>
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
                        <td colspan="3" class="text-start">Voucher giảm giá</td>
                        <td>{{ formatMoney(Number(order?.discount)) }}</td>
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
import {Order, OrderStatusText} from "@/base/model/order.model";
import {Product} from "@/base/model/product.model";
import {useRoute, useRouter} from "vue-router";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import formatMoney, {formatDate} from "@/plugins/utils";
import { format } from 'date-fns';

export default defineComponent({
    name: "OrderDetailComponent",
    components: {FooterComponent, HeaderComponent},
    setup() {
        return {
            router: useRouter(),
            route: useRoute(),
            orderService: inject('orderService') as OrderService,
            OrderStatusText
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
        formatMoney,
        formatDate,
        formatDateWithPattern(date : number){
            let pattern = 'dd/MM/yyyy HH:mm:ss';
            return format(new Date(date), pattern);
        }
    },
    created() {
        this.initOrder();
    }
});
</script>

<style>

</style>