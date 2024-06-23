<script lang="ts">
import {defineComponent, inject} from "vue";
import type {OrderService} from "@/base/service/order.service";
import {toast} from "vue3-toastify";
import {Order, OrderStatusText} from "@/base/model/order.model";
import formatMoney from "@/plugins/utils";
import {formatDate} from "date-fns";

export default defineComponent({
    name: 'OrderComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index', align: 'center'},
            {title: 'Username', value: 'username', align: 'center'},
            {title: 'Email', value: 'email', align: 'center'},
            {title: 'Địa chỉ', value: 'address', align: 'center'},
            {title: 'Ngày đặt', value: 'createDate', align: 'center'},
            {title: 'Tổng tiền', value: 'totalPayment', align: 'center'},
            {title: 'Trạng thái', value: 'status', align: 'center'},
            {title: 'Hành động', value: 'action', align: 'center'}
        ]
        const ORDER = Order;
        return {
            headers,
            ORDER,
            orderService: inject('orderService') as OrderService
        }
    },
    data() {
        return {
            orderPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
            } as any,
            searchParams: {
                page: 0,
                size: 10,
                startDate: '',
                endDate: '',
            },
            isLoading: false,
        }
    },
    methods: {
        async search() {
            this.isLoading = true;
            this.orderService.findBySearch(this.searchParams).then((data) => {
                this.orderPage = data;
            }).finally(() => {
                this.isLoading = false;
            });
        },
        viewDetail(item: any) {
            this.$router.push('/admin/order/' + item.id);
        },
        approve(id: string) {
            if (confirm('Bạn có chắc chắn muốn xác nhận đơn hàng này ?')) {
                this.orderService.approve(id).then(() => {
                    this.search();
                    toast.success('Xác nhận đơn hàng thành công');
                }).catch((error) => {
                    toast.error(error.response.data.message);
                });
            }
        },
        reject(id: any) {
            if (confirm('Bạn có chắc chắn muốn từ chối đơn hàng này ?')) {
                this.orderService.reject({id}).then(() => {
                    this.search();
                    toast.success('Từ chối đơn hàng thành công');
                }).catch((error) => {
                    toast.error(error.response.data.message);
                });
            }
        },
        OrderStatusText,
        formatMoney,
        formatDate
    },
    created() {
        this.search();
    }
})
</script>

<template>
    <div class="text-dark text-center">
        <div>
            <h1>Quản lý đơn hàng</h1>
        </div>
        <div class="container-fluid">
            <v-form>
                <v-row>
                    <v-col cols="6">
                        <v-text-field v-model="searchParams.startDate" label="Từ ngày" type="datetime-local"></v-text-field>
                    </v-col>
                    <v-col cols="6">
                        <v-text-field v-model="searchParams.endDate" label="Đến ngày" type="datetime-local"></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" class="mb-3">
                        <v-btn class="mx-2" @click="search" color="primary">Tìm kiếm</v-btn>
                        <v-btn @click="searchParams = {page: 0, size: 10, startDate: '', endDate: ''}" color="secondary">Xóa bộ lọc</v-btn>
                    </v-col>
                </v-row>
            </v-form>
            <v-data-table
                :headers="headers"
                :items="orderPage.content"
                :loading="isLoading"
                item-key="id"
                :items-per-page="searchParams.size"
                :show-current-page = "true"
                :items-per-page-text="`Số dòng trên trang:`"
                @update:page="search"
                :on-update:items-per-page="search"
            >
                <template v-slot:[`item.index`]="{ index }">
                    {{ index + 1 }}
                </template>
                <template v-slot:[`item.username`]="{ item }">
                    {{ item?.user?.username }}
                </template>
                <template v-slot:[`item.createDate`]="{ item }">
                    {{ formatDate(item?.createDate,'dd/MM/yyyy hh:MM:ss') }}
                </template>
                <template v-slot:[`item.totalPayment`]="{ item }">
                    {{ formatMoney(item?.totalPayment) }}
                </template>
                <template v-slot:[`item.email`]="{ item }">
                    {{ item?.user?.email || 'Không có' }}
                </template>

                <template v-slot:[`item.address`]="{ item }">
                    {{ item?.user?.address || 'Không có' }}
                </template>
                <template v-slot:[`item.status`]="{ item }">
                    {{ OrderStatusText(item?.status) }}
                </template>
                <template v-slot:[`item.action`]="{ item }">
                    <!--                   use icon-->
                    <v-icon @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon @click="approve(item.id)" v-if="ORDER.STATUS_CONFIRMED === item?.status">mdi-check</v-icon>
                    <v-icon @click="reject(item.id)" v-if="ORDER.STATUS_CONFIRMED === item?.status">mdi-close</v-icon>
                </template>
            </v-data-table>
        </div>
    </div>
</template>