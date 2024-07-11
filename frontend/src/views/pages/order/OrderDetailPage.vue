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
            <div class="mb-lg-4">
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
                            {{ item?.price }}
                        </td>
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
        }
    },
    created() {
        this.initOrder();
    }
});
</script>

<style>

</style>