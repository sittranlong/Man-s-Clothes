<script lang="ts">
import {defineComponent, inject} from "vue";
import HeaderComponent from "@/components/header/HeaderComponent.vue";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import formatMoney from "@/plugins/utils";
import {CartService} from "@/base/service/cart.service";
import {useRouter} from "vue-router";
import {toast} from "vue3-toastify";

export default defineComponent({
    name: 'CartPage',
    components: {
        HeaderComponent,
        FooterComponent
    },
    setup() {
        return {
            formatMoney,
            cartService: inject('cartService') as CartService,
            router: useRouter()
        }
    },
    data() {
        return {
            cartPage: {
                content: [],
                totalPages: 0,
                totalElements: 0,
                last: true,
                size: 0,
                number: 0,
                sort: null,
                first: true,
                numberOfElements: 0,
                empty: true,
            } as any,
            params: {
                page: 1,
                size: 10,
            },
            loading: false,
            error: null,
            cartIdList: [],
        }
    },
    methods: {
        async deleteCart(id: number) {
            this.cartService.delete(id).then((res) => {
                toast.success("Xóa thành công");
                this.getCart();
            }, (error) => {
                toast.error(error?.response?.data?.message);
            });
        },
        redirectToCheckout() {
            if (this.cartIdList.length === 0) {
                toast('Vui lòng chọn sản phẩm để thanh toán', {type: 'error'})
                return;
            } else {
                this.router.push('/checkout?cartIdList=' + this.cartIdList.join(','));
            }
        },
        async getCart() {
            this.loading = true;
            this.cartService.get(this.params).then((response) => {
                this.cartPage = response;
            }).catch((error) => {
                this.error = error;
            }).finally(() => {
                this.loading = false;
            });
        },
        changeQuantity(cartId: string, quantity: number) {
            if (isNaN(quantity) || quantity < 0) {
                toast('Số lượng không hợp lệ', {type: 'error'});
                this.getCart();
                return;
            }
            this.cartService.update({cartId, quantity}).then((res) => {
                toast.success(res);
            }, (error) => {
                toast.error(error?.response?.data?.message);
                this.getCart();
            });
        }
    },
    created() {
        this.getCart();
    }
});
</script>

<template>
    <div>
        <header-component/>
        <section class="h-100" style="background-color: #eee;">
            <div class="container h-100 py-5">
                <div class="row d-flex justify-content-center align-items-center h-100 mb-3">
                    <div class="col-10">
                        <div class="d-flex justify-content-between align-items-center">
                            <h3 class="fw-normal mt-4 text-black">Giỏ hàng</h3>
                        </div>
                        <div v-if="loading" class="text-center">
                            <p>Đang load, đợi tí nhá</p>
                        </div>
                        <div v-if="cartPage?.content?.length !== 0 && !loading && !error">
                            <div class="card rounded-3 mb-4" v-for="(item, index) in cartPage?.content" :key="index">
                                <div class="card-body p-4">
                                    <div class="row d-flex justify-content-between align-items-center">
                                        <div class="col-md-1">
                                            <input type="checkbox" class="check" :value="item.id" v-model="cartIdList">
                                        </div>
                                        <div class="col-md-2 col-lg-2 col-xl-2">
                                            <v-img :src="'data:image/png;base64,' + item.productDetail?.product?.image"
                                                   class="img-fluid" alt="product"></v-img>
                                        </div>
                                        <div class="col-md-3 col-lg-3 col-xl-3">
                                            <p class="lead fw-normal mb-2">{{ item?.productDetail?.product?.name }}</p>
                                            <p><span class="text-muted">Size: </span>{{
                                                    item?.productDetail?.size.name
                                                }}<span
                                                    class="text-muted">Color: </span>{{ item.productDetail.color.name }}
                                            </p>
                                        </div>
                                        <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                                            <button class="btn btn-link px-2"
                                                    @click.prevent="item.quantity--;changeQuantity(item.id,item.quantity)">
                                                <i class="bi bi-dash"></i>
                                            </button>
                                            <input min="0" name="quantity" v-model="item.quantity" type="number"
                                                   class="form-control form-control-sm"
                                                   @change.prevent="changeQuantity(item.id,item.quantity)"/>
                                            <button class="btn btn-link px-2"
                                                    @click.prevent="item.quantity++;changeQuantity(item.id,item.quantity)">
                                                <i class="bi bi-plus"></i>
                                            </button>
                                        </div>
                                        <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                                            <h5 title="Giá" class="mb-0">Giá : <span class="fw-bold">{{
                                                    formatMoney(item?.productDetail?.product?.price * item.quantity)
                                                }}</span></h5>
                                        </div>
                                        <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                                            <a role="button" title="Xóa" class="text-danger"
                                               @click.prevent="deleteCart(item?.id)"><i class="bi bi-trash"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div v-if="cartPage?.content?.length !== 0 && !loading && !error">
                            <div class="card-body">
                                <button type="button" class="btn btn-warning btn-block btn-lg"
                                        @click.prevent="redirectToCheckout()">Thanh toán
                                </button>
                            </div>
                        </div>
                        <div class="card" v-if="cartPage?.content?.length === 0 && !loading && !error">
                            <div class="card-body">
                                <p class="lead fw-normal mb-2">Giỏ hàng trống</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <footer-component/>
    </div>
</template>

<style scoped>
@import "style.css";
</style>
