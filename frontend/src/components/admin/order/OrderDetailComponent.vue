<script lang="ts">
import {defineComponent, inject} from 'vue';
import {OrderService} from "@/base/service/order.service";
import {Order, OrderStatusText} from "@/base/model/order.model";
import formatMoney from "@/plugins/utils";
import {CustomerTypeText} from "../../../base/model/customer.model";

export default defineComponent({
    name: 'OrderDetailComponent',
    setup() {
        const ORDER = Order
        return {
            ORDER,
            orderService: inject('orderService') as OrderService,
        }
    },
    data() {
        return {
            order: new Order(),
            id: this.$route.params.id as string
        }
    },
    methods: {
        CustomerTypeText,
        formatMoney,
        OrderStatusText,
        async getOrder() {
            this.order = await this.orderService.getDetail(this.id);
        },
        approve(id: string | undefined) {
            if (id) {
                if (confirm('Bạn có chắc chắn muốn xác nhận đơn hàng này ?')) {
                    this.orderService.approve(id).then(() => {
                        this.getOrder();
                    });
                }
            }
        },
        reject(id: string | undefined) {
            if (id) {
                if (confirm('Bạn có chắc chắn muốn từ chối đơn hàng này ?')) {
                    this.orderService.reject({id}).then(() => {
                        this.getOrder();
                    });
                }
            }
        }
    },
    created() {
        this.getOrder();
    }
});
</script>

<template>
    <div class="text-dark">
        <div class="flex justify-between items-center">
            <h1 class="text-2xl font-bold">Chi tiết đơn hàng</h1>
        </div>
        <div class="mt-5">
            <div class="flex justify-between">
                <v-card>
                    <v-card-title>
                        <p><span class="font-bold">Mã đơn hàng:</span> {{ order.id }}</p>
                        <p><span class="font-bold">Ngày đặt:</span> {{ order.createDate }}</p>
                        <p><span class="font-bold">Tên tour:</span> {{ order?.tour?.name }}</p>
                        <p><span class="font-bold">Tổng tiền:</span> {{ formatMoney(order.totalPayment) }}</p>
                        <p><span class="font-bold">Trạng thái:</span> {{ OrderStatusText(order.status) }}</p>
                    </v-card-title>
                </v-card>
            </div>
            <div class="mt-5 d-flex justify-content-center align-items-center flex-column">
                <h2 class="text-center">Danh sách khách hàng</h2>
                <table class="table-auto mt-3">
                    <thead>
                    <tr>
                        <th class="border px-4 py-2 fw-bold">STT</th>
                        <th class="border px-4 py-2 fw-bold">Họ tên</th>
                        <th class="border px-4 py-2 fw-bold">Địa chỉ</th>
                        <th class="border px-4 py-2 fw-bold">Email</th>
                        <th class="border px-4 py-2 fw-bold">Loại khách hàng</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(customer, index) in order.customers" :key="customer.id">
                        <td class="border px-4 py-2">{{ index + 1 }}</td>
                        <td class="border px-4 py-2">{{ customer.name }}</td>
                        <td class="border px-4 py-2">{{ customer.address }}</td>
                        <td class="border px-4 py-2">{{ customer.email }}</td>
                        <td class="border px-4 py-2">{{ CustomerTypeText(customer.type) }}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="d-flex justify-content-center align-items-center mt-3">
        <router-link to="/admin/order" class="btn btn-primary">Quay lại</router-link>
       <v-btn color="green-darken-3" v-if="ORDER.STATUS_PENDING === order?.status">
           <v-icon @click="approve(order.id)">mdi-check</v-icon>
       </v-btn>
       <v-btn color="blue-grey-lighten-2" v-if="ORDER.STATUS_PENDING === order?.status">
           <v-icon @click="reject(order.id)" >mdi-close</v-icon>
       </v-btn>
    </div>
</template>