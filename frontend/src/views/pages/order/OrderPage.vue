<template>
    <header-component/>
    <section class="container my-3">
        <div class="h3 text-center">
            Danh sách đơn hàng
        </div>
        <div class="table-responsive mt-lg-5">
            <table v-if="orderPage?.content?.length > 0" class="table table-striped table-hover table-bordered">
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã đơn hàng</th>
                    <th>Địa chỉ</th>
                    <th>SĐT</th>
                    <th>Họ tên</th>
                    <th>Ngày tạo</th>
                    <th>Hình thức thanh toán</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                    <th>Xem đơn hàng</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(item,index) in orderPage.content" :key="index">
                    <td>{{ index + 1}}</td>
                    <td>{{ item.code }}</td>
                    <td>{{ item.address }}</td>
                    <td>{{ item.phone }}</td>
                    <td>{{ item.name }}</td>
                    <td>{{ dateTimeFormat(item.created) }}</td>
                    <td>{{ item.paymentMethod }}</td>
                    <td>
					<span class="badge"
                          :class="[
                              {'bg-warning': item.status === Order.STATUS_PENDING}
                              ,{'bg-success': item.status === Order.STATUS_PROCESSING
                              || item.status === Order.STATUS_SHIPPING
                              || item.status === Order.STATUS_COMPLETED},
                              {'bg-danger': item.status === Order.CANCELLED},
					      {'bg-danger' : item.status === Order.STATUS_REFUNDED }]">
						{{ OrderStatusText(item.status) }}
					</span>
                    </td>
                    <td>
                        <button v-if="item.status === Order.STATUS_PROCESSING" title="Yêu cầu hoàn hàng" class="btn btn-warning"
                                data-bs-toggle="modal" data-bs-target="#exampleModal" @click.prevent="idCurrent  = item.id">
                            <i class="bi bi-arrow-counterclockwise"></i>
                        </button>
                        <button v-if="item.status === Order.STATUS_PENDING" title="Hủy đơn hàng" class="btn btn-warning"
                                @click.prevent="cancel(item.id)">
                            <i class="bi bi-archive-fill"></i>
                        </button>
                        <button v-if="item.status === Order.STATUS_COMPLETED" title="Đã nhận được hàng" class="btn btn-warning"
                                @click.prevent="receivedOrder(item.id)">
                            <i class="bi bi-bag-check"></i>
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-primary" @click.prevent="router.push('/order/' + item.id)">
                            <i class="bi bi-eye-fill"></i>
                        </button>
                    </td>
                </tr>
                </tbody>
                <tr>
                </tr>
            </table>
        </div>
        <div class="container mt-lg-5">
            <div class="row">
                <div class="col-md-12">
                    <div class="d-flex justify-content-between">
                        <select @change.prevent="findAll()" v-model="size">
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                        </select>
                        <div class="d-flex">
                            <button class="btn btn-primary" @click.prevent="prePage()">
                                <i class="bi bi-arrow-left"></i>
                            </button>
                            <div v-for=" (item,index) in orderPage.totalPages" :key="index">
                                <button class="btn btn-primary" @click.prevent="changePage(item - 1)">
                                    {{ item }}
                                </button>
                            </div>
                            <button class="btn btn-primary" @click.prevent="nextPage()">
                                <i class="bi bi-arrow-right"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Lý do trả hàng</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
					<textarea class="w-100" placeholder="Lý do trả hàng" v-model="note">
					</textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button class="btn btn-primary" @click.prevent="returnOrder(idCurrent)">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <footer-component/>
</template>

<script lang="ts">
import {defineComponent, inject} from "vue";
import {Order, OrderStatusText} from "@/base/model/order.model";
import {OrderService} from "@/base/service/order.service";
import {toast} from "vue3-toastify"
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import {useRouter} from "vue-router";

export default defineComponent({
    name: "OrderListView",
    components: {FooterComponent, HeaderComponent},
    setup() {
        return {
            OrderStatusText,
            toast,
            orderService : inject('orderService') as OrderService,
            router :useRouter()
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
            idCurrent: 0,
            page: 0,
            size: 10,
            Order : Order,
            note : "" as string
        }
    },
    methods: {
        findAll() {
            this.orderService.findByUser({page :this.page, size :this.size}).then(res => {
                this.orderPage = res;
            });
        },
        dateTimeFormat(date: Date) {
            return new Date(date).toLocaleString();
        },
        prePage() {
            if (this.page > 0) {
                this.page--;
                this.findAll();
            }
        },
        nextPage() {
            if(this.orderPage.totalPages){
                if (this.page < this.orderPage.totalPages - 1) {
                    this.page++;
                    this.findAll();
                }
            }
        },
        changePage(page: number) {
            this.page = page;
            this.findAll();
        },
        cancel(id: number) {
            if(confirm("Bạn có chắc chắn muốn hủy đơn hàng này?")){
                this.orderService.cancelOrder(id).then(res => {
                    if (res) {
                        this.findAll();
                    }
                });
            }
        },
        returnOrder(id: number) {
            if(confirm("Bạn có chắc chắn muốn trả lại đơn hàng này?")){
                // this.orderService.(id,this.note).then(res => {
                //     if (res) {
                //         this.findAll();
                //     }
                // });
            }
        },
        receivedOrder(id: number) {
            if(confirm("Bạn có chắc chắn muốn xác nhận đã nhận hàng?")){
                // this.orderService.receiveOrder(id).then(res => {
                //     if (res) {
                //         this.findAll();
                //     }
                // });
            }
        }
    },
    created() {
        this.findAll();
    }
});
</script>

<style scoped>

</style>