<script lang="tsx">
import {defineComponent, inject, ref} from "vue";
import {ProductCard} from "@/base/model/product.model";
import RatingComponent from "@/components/rating/RatingComponent.vue";
import formatMoney from "@/plugins/utils";
import DialogComponent from "@/components/dialog/DialogComponent.vue";
import {toast} from 'vue3-toastify';
import {WishlistService} from "@/base/service/wishlist.service";
import {useRouter} from "vue-router";

export default defineComponent({
    name: 'ProductComponent',
    components: {
        RatingComponent,
        DialogComponent
    },
    props: {
        product: {
            type: ProductCard,
            default: () => new ProductCard(),
            required: true
        }
    },
    setup(props, {emit}) {
        const product = ref(props.product);
        const wishListService = inject('wishlistService') as WishlistService;
        const show = ref(false);
        const addToWishlist = () => {
            emit('add-to-wishlist', product.value);
            show.value = true;
            if (product.value?.id) {
                wishListService.add(product.value.id).then((response) => {
                    toast.success(response);
                } ).catch((error) => {
                    toast.error(error?.response?.data?.message);
                });
            }
        }
        const router = useRouter();
        return () => (
            <div>
                <div class="product-card border-1 mb-7">
                    <div role="button" onclick={()=> router.push("/product/" + product.value.id)} title={product.value?.name} class="product-image"
                         style={{"background-image": 'url(data:image/png;base64,' + product.value?.image + ')'}}></div>
                    <div class="product-info">
                        <div class="product-title limit-line-1">{product.value?.name}</div>
                        <div class="product-price">{formatMoney(product.value?.price)}</div>
                        <div class="product-description limit-line-2">{product.value?.description}</div>
                        <div class="product-rating d-flex">
                            <div>
                                <rating-component rating={product.value?.rating?.rate}/>
                                <span class="total-reviews">({product.value?.rating?.count} reviews)</span>
                            </div>
                            <div class="ml-auto">
                                <v-btn onClick={addToWishlist} title="Thêm vào danh sách yêu thích" density="default"
                                       icon="mdi-heart-outline"/>
                            </div>
                        </div>
                    </div>
                </div>
                <dialog-component show={show} title="Add to cart" message="Add to cart success"/>
            </div>
        );
    }
});
</script>

<style lang="scss" scoped>
@import "style";
</style>
