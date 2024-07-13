<template>
    <div class="container mt-5 mb-5">
        <div class="card">
            <div class="row g-0">
                <div class="col-md-6 border-end">
                    <div class="d-flex flex-column justify-content-center">
                        <div class="main_image">
                            <!--                            <img :src="'data:image/png;base64,' + imageDefault" alt="product image"-->
                            <!--                                 width="450">-->
                            <v-img :src="'data:image/png;base64,' + imageDefault" alt="product image" :cover="true"
                                   width="450"/>
                        </div>
                        <div class="thumbnail_images d-flex">
                            <ul>
                                <li v-for="(item,index) in product?.productDetails" :key="index">
                                    <v-img :src="'data:image/png;base64,' + item.image" alt="product image"
                                           :cover="true"
                                           width="70" height="70" @click="imageDefault = item.image"/>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 border-end d-flex justify-content-center">
                    <div class="px-10 right-side">
                        <div class="d-flex justify-content-between align-items-center"><h4>{{ product?.name }}</h4><span
                            class="heart"><i
                            class='bx bx-heart'></i></span></div>
                        <div class="mt-2 pr-3 content"><p>
                            {{ product.description }}
                        </p></div>
                        <div class="text-start">
                            Giá bán: <span class="fw-bold"><strike>
                            {{
                                formatMoney(product.price - (product.price * product.discountPercent / 100))
                            }}
                        </strike> </span><br>
                            Giá gốc: <span class="fw-bold"> {{ formatMoney(product.price) }}</span>
                        </div>
                        <div class="text-start">
                            Giảm giá: <span class="fw-bold text-danger">{{ product.discountPercent + '%' }}</span>
                        </div>
                        <!--						thuôc tính-->
                        <div class="text-start">
                            Danh mục : <span class="fw-bold">{{ product.category?.name }}</span>
                        </div>
                        <div class="text-start">
                            Thương hiệu : <span class="fw-bold">{{ product.brand?.name }}</span>
                        </div>
                        <div v-if="product?.productDetails?.length > 0" class="text-start">
                            <span class="fw-bold">Kích cỡ</span>
                            <div class="d-flex flex-row ">
                                <v-btn v-for="(item,index) in product?.productDetails" :key="index"
                                       @click.prevent="size = item.size.name;getQuantity()">
                                    {{ item?.size?.name }}
                                </v-btn>
                            </div>
                        </div>
                        <div v-if="product?.productDetails?.length" class="text-start">
                            <span class="fw-bold">Màu sắc</span>
                            <div class="d-flex flex-row ">
                                <v-btn v-for="(item,index) in product?.productDetails" :key="index"
                                       @click.prevent="color = item.color.name;getQuantity()"
                                       :style="'background-color:'+ item?.color?.code">
                                </v-btn>
                            </div>
                        </div>
                        <div class="text-start">
                            Số lượng còn : <span class="fw-bold">{{ quantity }}</span>
                        </div>
                        <div class="text-start">
                            Mô tả : <span
                            class="fw-bold">{{ product.description == null ? 'Không có' : product.description }}</span>
                        </div>
                        <!--						 chọn số lượng kiểu số-->
                        <div class="text-start">
                            <span>Chọn số lượng : </span>
                            <div class="d-flex justify-content-start">
                                <button class="btn btn-outline-dark" @click="userQuantity = userQuantity - 1"
                                        :disabled="userQuantity <= 0">-
                                </button>
                                <input type="text" class="form-control w-25" v-model="userQuantity" min="0"
                                       :max="quantity">
                                <button class="btn btn-outline-dark" @click="userQuantity = userQuantity + 1"
                                        :disabled="userQuantity >= quantity">+
                                </button>
                            </div>

                        </div>
                        <div class="ratings d-flex flex-row align-items-center">
                            <div class="d-flex flex-row"><i class='bx bxs-star'></i> <i class='bx bxs-star'></i> <i
                                class='bx bxs-star'></i> <i class='bx bxs-star'></i> <i class='bx bx-star'></i></div>
                        </div>
                        <div class="buttons d-flex flex-row mt-5 gap-3">
                            <button type="button" class="btn btn-dark text-light" @click.prevent="addToCart()">Thêm vào
                                giỏ hàng
                            </button>
                        </div>
                        <div class="search-option"><i class='bx bx-search-alt-2 first-search'></i>
                            <div class="inputs"><input type="text" name=""></div>
                            <i class='bx bx-share-alt share'></i></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer-component/>
</template>

<script lang="ts">
import {defineComponent, inject} from "vue";
import {ProductDetail} from "@/base/model/product-detail.model";
import {Product} from "@/base/model/product.model";
import {ProductService} from "@/base/service/product.service";
import {CartService} from "@/base/service/cart.service";
import {toast} from "vue3-toastify";
import {useRoute} from "vue-router";
import FooterComponent from "@/components/footer/FooterComponent.vue";

export default defineComponent({
    name: "ProductDetailComponent",
    components: {FooterComponent},
    setup() {
        return {
            route: useRoute(),
            productService: inject('productService') as ProductService,
            cartService: inject('cartService') as CartService
        }
    },
    data() {
        return {
            productId: this.route.params.id as string,
            Product: Product,
            product: new Product(),
            productDetailList: new Array<ProductDetail>(),
            color: '',
            size: '',
            quantity: 0 as any,
            userQuantity: 0 as any,
            productDetailId: null as any,
            imageDefault: '' as any
        }
    },
    methods: {
        getProduct() {
            this.productService.get(this.productId).then((response) => {
                this.product = response;
                this.imageDefault = this.product?.image as string;
            }).catch((error) => {
                console.log(error);
            });
        },
        formatMoney(money: number) {
            return money?.toLocaleString('it-IT', {style: 'currency', currency: 'VND'});
        },
        getQuantity() {
            let flag = false;
            if (this.product?.productDetails && this.product?.productDetails.length > 0) {
                this.product?.productDetails?.forEach((item) => {
                    if (item.color?.name == this.color && item.size?.name == this.size) {
                        flag = true;
                        this.quantity = item.quantity;
                        this.productDetailId = item.id;
                    }
                });
                if (!flag) {
                    this.quantity = 0;
                }
            }
        },
        addToCart() {
            const payload = {
                productDetailId: this.productDetailId,
                quantity: this.userQuantity
            } as any;
            this.cartService.add(payload).then((response) => {
                toast.success(response);
            }, error => {
                toast.error(error.response);
            })
        }
    },
    created() {
        this.getProduct();
    }
});
</script>

<style scoped>

.card {
    border: none;
    overflow: hidden
}

.thumbnail_images ul {
    list-style: none;
    justify-content: center;
    display: flex;
    align-items: center;
    margin-top: 10px
}

.thumbnail_images ul li {
    margin: 5px;
    padding: 10px;
    border: 2px solid #eee;
    cursor: pointer;
    transition: all 0.5s
}

.thumbnail_images ul li:hover {
    border: 2px solid #000
}

.main_image {
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom: 1px solid #eee;
    height: 400px;
    width: 100%;
    overflow: hidden
}

.heart {
    height: 29px;
    width: 29px;
    background-color: #eaeaea;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center
}

.content p {
    font-size: 12px
}

.ratings span {
    font-size: 14px;
    margin-left: 12px
}

.colors ul {
    list-style: none;
    display: flex;
    padding-left: 0px
}

.colors ul li {
    height: 20px;
    width: 20px;
    display: flex;
    border-radius: 50%;
    margin-right: 10px;
    cursor: pointer
}

.colors ul li:nth-child(1) {
    background-color: #6c704d
}

.colors ul li:nth-child(2) {
    background-color: #96918b
}

.colors ul li:nth-child(3) {
    background-color: #68778e
}

.colors ul li:nth-child(4) {
    background-color: #263f55
}

.colors ul li:nth-child(5) {
    background-color: black
}

.right-side {
    position: relative
}

.search-option {
    position: absolute;
    background-color: #000;
    overflow: hidden;
    align-items: center;
    color: #fff;
    width: 200px;
    height: 200px;
    border-radius: 49% 51% 50% 50% / 68% 69% 31% 32%;
    left: 30%;
    bottom: -250px;
    transition: all 0.5s;
    cursor: pointer
}

.search-option .first-search {
    position: absolute;
    top: 20px;
    left: 90px;
    font-size: 20px;
    opacity: 1000
}

.search-option .inputs {
    opacity: 0;
    transition: all 0.5s ease;
    transition-delay: 0.5s;
    position: relative
}

.search-option .inputs input {
    position: absolute;
    top: 200px;
    left: 30px;
    padding-left: 20px;
    background-color: transparent;
    width: 300px;
    border: none;
    color: #fff;
    border-bottom: 1px solid #eee;
    transition: all 0.5s;
    z-index: 10
}

.search-option .inputs input:focus {
    box-shadow: none;
    outline: none;
    z-index: 10
}

.search-option:hover {
    border-radius: 0px;
    width: 100%;
    left: 0px
}

.search-option:hover .inputs {
    opacity: 1
}

.search-option:hover .first-search {
    left: 27px;
    top: 25px;
    font-size: 15px
}

.search-option:hover .inputs input {
    top: 20px
}

.search-option .share {
    position: absolute;
    right: 20px;
    top: 22px
}

.buttons .btn {
    height: 50px;
    width: 150px;
    border-radius: 0px !important
}
</style>