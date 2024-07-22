<template>
    <div class="container mt-5 mb-5">
        <div class="card">
            <div class="row g-0">
                <div class="col-md-6 border-end">
                    <div class="d-flex flex-column justify-content-center">
                        <div class="main_image">
                            <v-img :src="'data:image/png;base64,' + imageDefault" alt="product image" :cover="true"
                                   width="450"/>
                        </div>
                        <div class="thumbnail_images d-flex">
                            <ul>
                                <li v-for="(item,index) in product?.productDetails" :key="index">
                                    <v-img
                                        :src="'data:image/png;base64,' + item.image"
                                        alt="product image"
                                        :cover="true"
                                        width="70"
                                        height="70"
                                        @click="imageDefault = item.image"
                                    />
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="col-md-6 border-end d-flex justify-content-center">
                    <div class="px-10 right-side">
                        <div class="d-flex justify-content-between align-items-center">
                            <h4>{{ product?.name }}</h4>
                            <span class="heart"><i class="bx bx-heart"></i></span>
                        </div>
                        <div class="text-start">
                            Giá bán: <span class="fw-bold">{{
                                formatMoney(product.price - (product.price * product.discountPercent / 100))
                            }}</span><br/>
                            Giá gốc: <span class="fw-bold"><strike>{{ formatMoney(product.price) }}</strike> </span>
                        </div>
                        <div class="text-start">
                            Giảm giá: <span class="fw-bold text-danger">{{ product.discountPercent + '%' }}</span>
                        </div>
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
                            Mô tả : <span class="fw-bold"
                                          v-html="product.description == null ? 'Không có' : product.description"></span>
                        </div>
                        <div class="text-start">
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
                            <div class="d-flex flex-row">
                                <i class='bx bxs-star'></i> <i class='bx bxs-star'></i> <i class='bx bxs-star'></i> <i
                                class='bx bxs-star'></i> <i class='bx bx-star'></i>
                            </div>
                        </div>
                        <div class="buttons d-flex flex-row mt-5 gap-3">
                            <button type="button" class="btn btn-dark text-light" @click.prevent="addToCart()">Thêm vào
                                giỏ hàng
                            </button>
                        </div>
                        <div class="search-option">
                            <i class='bx bx-search-alt-2 first-search'></i>
                            <div class="inputs"><input type="text" name=""></div>
                            <i class='bx bx-share-alt share'></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Review Product here -->
        <div class="review-section my-5">
            <div class="review-dialog">
                <!-- Trigger button to open the dialog -->
                <v-btn color="primary" @click="dialog = true">Đánh giá sản phẩm</v-btn>

                <!-- Dialog component -->
                <v-dialog v-model="dialog" max-width="600px">
                    <v-card>
                        <v-card-title class="headline">Đánh giá sản phẩm</v-card-title>
                        <v-card-subtitle>Vui lòng để lại nhận xét và đánh giá của bạn.</v-card-subtitle>
                        <v-card-text>
                            <v-form @submit.prevent="submitReview">
                                <v-textarea v-model="review.content" label="Nhận xét của bạn" required></v-textarea>
                                <v-file-input class="w-25" v-model="files" label="Ảnh đính kèm"
                                              accept="image/*"></v-file-input>
                                <div class="d-flex rating align-items-center mt-3">
                                    <label class="me-2">Đánh giá:</label>
                                    <v-rating :active-color="'red'" v-model="review.rating" :length="5" :size="32"
                                              :hover="true"></v-rating>
                                </div>
                                <div class="actions mt-3 d-flex justify-content-end">
                                    <v-btn type="submit" color="primary">Gửi đánh giá</v-btn>
                                    <v-btn @click="dialog = false" class="ms-2" text>Hủy</v-btn>
                                </div>
                            </v-form>
                        </v-card-text>
                    </v-card>
                </v-dialog>
            </div>
            <div class="reviews my-5">
                <h3 class="mb-3">Đánh giá sản phẩm</h3>
                <v-card v-for="item in reviews" :key="item.id" class="mb-3">
                    <v-card-title>
                        <span class="headline">
                            <v-avatar size="36">
                                <v-img :src="'data:image/png;base64,' + item.user.avatar" alt="avatar"></v-img>
                                </v-avatar>
                            {{ item?.user.fullName }}</span>
                        <v-rating :active-color="'red'" :model-value="item.rating" :readonly="true" class="ml-2"
                                  :size="24"></v-rating>
                    </v-card-title>
                    <v-card-text>{{ item.content }}</v-card-text>
                    <v-img :src="'data:image/png;base64,' + item.image" alt="review image" width="100" height="100"/>
                </v-card>
                <div v-if="!reviews || reviews.length === 0">
                    <p>Chưa có đánh giá nào cho sản phẩm này.</p>
                </div>
            </div>
        </div>
    </div>
    <footer-component/>
</template>

<script lang="ts">
import {defineComponent, inject} from "vue";
import {Product, ProductDetail} from "@/base/model/product.model";
import {ProductService} from "@/base/service/product.service";
import {CartService} from "@/base/service/cart.service";
import {toast} from "vue3-toastify";
import {useRoute} from "vue-router";
import FooterComponent from "@/components/footer/FooterComponent.vue";
import {useStore} from "vuex";
import {ReviewService} from "@/base/service/review.service";
import {Review} from "@/base/model/review.model";

export default defineComponent({
    name: "ProductDetailComponent",
    components: {FooterComponent},
    setup() {
        return {
            route: useRoute(),
            productService: inject('productService') as ProductService,
            cartService: inject('cartService') as CartService,
            reviewService: inject('reviewService') as ReviewService,
            store: useStore()
        };
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
            imageDefault: '' as any,
            review: new Review(),
            reviews: [] as Review[],
            files: [] as File[],
            dialog: false,
            isLogin: this.store.getters.isAuthenticated
        };
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
        },
        async submitReview() {
            if (!this.isLogin) {
                toast.error('Vui lòng đăng nhập để đánh giá sản phẩm.');
                return;
            }
            if (this.files.length > 0) {
                const reader = new FileReader();
                const readerPromise = new Promise((resolve, reject) => {
                    reader.onload = () => {
                        resolve(reader.result);
                    };
                    reader.onerror = (error) => {
                        reject(error);
                    };
                });
                reader.readAsDataURL(this.files[0]);
                try {
                    const imageData = await readerPromise;
                    this.review.imageBase64 = imageData as string;
                } catch (error) {
                    toast.error('Error reading file');
                }
                this.review.productId = this.productId;
                this.reviewService.create(this.review).then((res) => {
                    toast.success(res?.message);
                    this.dialog = false;
                }, error => {
                    toast.error(error.response.data.message);
                    this.dialog = false;
                });
            }
        },
        findAllReviews(){
            this.reviewService.findByProductId(this.productId).then((response) => {
                this.reviews = response;
            });
        }
    },
    created() {
        this.getProduct();
        this.findAllReviews();
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

.review-form {
    max-width: 600px;
    margin: 0 auto;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    background-color: #ffffff;
}

.rounded-border {
    border-radius: 8px;
}

.shadow {
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.v-textarea, .v-file-input {
    margin-bottom: 16px;
}

.rating {
    margin-top: 16px;
}

.actions {
    margin-top: 24px;
}
</style>