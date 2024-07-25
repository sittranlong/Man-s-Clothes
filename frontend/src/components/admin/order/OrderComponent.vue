<script lang="ts">
import {defineComponent, inject} from "vue";
import {OrderService} from "@/base/service/order.service";
import {toast} from "vue3-toastify";
import {Order, OrderStatusText, PaymentMethod, PaymentMethodText} from "@/base/model/order.model";
import formatMoney from "@/plugins/utils";
import {useRoute, useRouter} from "vue-router";

export default defineComponent({
    name: 'OrderComponent',
    setup() {
        const headers = [
            {title: 'STT', value: 'index', align: 'center'},
            {title: 'Mã đơn', value: 'code', align: 'center'},
            {title: 'Hình thức thanh toán', value: 'paymentMethod', align: 'center'},
            {title: 'Ngày đặt', value: 'createDate', align: 'center'},
            {title: 'Tổng tiền', value: 'totalPayment', align: 'center'},
            {title: 'Trạng thái', value: 'status', align: 'center'},
            {title: 'Hành động', value: 'action', align: 'center'}
        ]
        const ORDER = Order;
        const formatDate = (date: string) => {
            return new Date(date).toLocaleString();
        }
        return {
            headers,
            ORDER,
            orderService: inject('orderService') as OrderService,
            router: useRouter(),
            route: useRoute(),
            formatDate
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
                page: 1,
                size: 10,
                startDate: '',
                endDate: '',
                keyword: ''
            },
            isLoading: false,
            dialog: false,
            desc : '',
            orderCurrent: {} as Order
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
            this.router.push('/admin/order/' + item.id);
        },
        approve(order: Order) {
            if (PaymentMethod.QRCODE.value !== order.paymentMethod) {
                if (confirm('Bạn có chắc chắn muốn xác nhận đơn hàng này ?')) {
                    this.orderService.approve({id: order.id}).then(() => {
                        this.search();
                        toast.success('Xác nhận đơn hàng thành công');
                    }).catch((error) => {
                        toast.error(error.response.data.message);
                    });
                }
            } else {
                this.dialog = true;
                this.desc = order?.qrCodeDesc || '';
                this.orderCurrent = order;
            }
        },
        approvedQRCode(){
            this.orderService.approve({id: this.orderCurrent.id}).then(() => {
                this.search();
                toast.success('Xác nhận đơn hàng thành công');
            }).catch((error) => {
                toast.error(error.response.data.message);
            }).finally(() => {
                this.dialog = false;
                this.desc = '';
                this.orderCurrent = {} as Order;
            });
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
        shipping(id: any) {
            if (confirm('Bạn có chắc chắn muốn xác nhận đơn hàng này đã giao hàng ?')) {
                this.orderService.shipping({id}).then(() => {
                    this.search();
                    toast.success('Xác nhận giao hàng thành công');
                }).catch((error) => {
                    toast.error(error.response.data.message);
                });
            }
        },
        OrderStatusText,
        formatMoney,
        PaymentMethodText
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
                    <v-col cols="12">
                        <v-text-field v-model="searchParams.keyword" label="Tìm kiếm theo mã"></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="6">
                        <v-text-field v-model="searchParams.startDate" label="Từ ngày"
                                      type="datetime-local"></v-text-field>
                    </v-col>
                    <v-col cols="6">
                        <v-text-field v-model="searchParams.endDate" label="Đến ngày"
                                      type="datetime-local"></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="12" class="mb-3">
                        <v-btn class="mx-2" @click="search" color="primary">Tìm kiếm</v-btn>
                        <v-btn @click="searchParams = {page: 1, size: 10, startDate: '', endDate: ''}"
                               color="secondary">Xóa bộ lọc
                        </v-btn>
                    </v-col>
                </v-row>
            </v-form>
            <v-data-table-server
                :headers="headers"
                :items="orderPage.content"
                :loading="isLoading"
                v-model:items-per-page="searchParams.size"
                v-model:page="searchParams.page"
                :items-length="orderPage.totalElements"
                :items-per-page="searchParams.size"
                @update:itemsPerPage="search"
                @update:page="search"
                :on-update:items-per-page="search"
            >
                <template v-slot:[`item.index`]="{ index }">
                    {{ index + 1 }}
                </template>
                <template v-slot:[`item.paymentMethod`]="{ item }">
                    {{ PaymentMethodText(item?.paymentMethod) }}
                </template>
                <template v-slot:[`item.createDate`]="{ item }">
                    {{ formatDate(item?.createDate) }}
                </template>
                <template v-slot:[`item.totalPayment`]="{ item }">
                    {{ formatMoney(item?.finalTotal) }}
                </template>
                <template v-slot:[`item.status`]="{ item }">
                    {{ OrderStatusText(item?.status) }}
                </template>
                <template v-slot:[`item.action`]="{ item }">
                    <!--                   use icon-->
                    <v-icon @click="viewDetail(item)">mdi-eye</v-icon>
                    <v-icon @click="approve(item)" v-if="ORDER.STATUS_PENDING === item?.status">mdi-check</v-icon>
                    <v-icon @click="reject(item.id)" v-if="ORDER.STATUS_PENDING === item?.status">mdi-close</v-icon>
                    <v-icon @click="shipping(item.id)" v-if="ORDER.STATUS_PROCESSING === item?.status">mdi-truck
                    </v-icon>
                </template>
            </v-data-table-server>
        </div>
        <!--        dialog confirm order-->
        <v-dialog v-model="dialog" max-width="800px">
            <!--            submit buuton-->
            <v-card>
                <v-card-title>
                    <h3>Lưu ý</h3>
                </v-card-title>
                <v-card-text>
                    <div>
                        <p>
                            Bạn đang xác nhận đơn hàng sử dụng phương thức <b class="text-danger">thanh toán chuyển khoản qua QRCode.</b>
                        </p>
                        <v-text-field v-model="desc" label="Nội dung chuyển khoản" :readonly="true"></v-text-field>
                        <p>
                            Bạn hãy kiểm tra xem có giao dịch chuyển khoản nào khớp với nội dung trên không, nếu có hãy xác nhận đơn hàng.
                        </p>
                    </div>
                </v-card-text>
                <v-card-actions>
                    <v-btn color="primary" @click="dialog = false">Hủy</v-btn>
                    <v-btn color="primary" @click="approvedQRCode()">Xác nhận</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>