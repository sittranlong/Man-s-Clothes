<script lang="tsx">
import {defineComponent, ref} from "vue";
import {ProductCard} from "@/base/model/product.model";
import RatingComponent from "@/components/rating/RatingComponent.vue";
import formatMoney from "@/plugins/utils";
import DialogComponent from "@/components/dialog/DialogComponent.vue";
import {toast} from 'vue3-toastify';

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
        const show = ref(false);
        const addToCart = () => {
            emit('add-to-cart', product.value);
            localStorage.setItem('cart', JSON.stringify(product.value)); // TODO: remove this line when call api
            show.value = true;
            toast.success('Add to cart success');
        }
        return () => (
            <div>
                <div class="product-card border-1 mb-7">
                    <div role="button" title={product.value?.title} class="product-image"
                         style={{"background-image": 'url(' + product.value?.image + ')'}}></div>
                    <div class="product-info">
                        <div class="product-title limit-line-1">{product.value?.title}</div>
                        <div class="product-price">{formatMoney(product.value?.price)}</div>
                        <div class="product-description limit-line-2">{product.value?.description}</div>
                        <div class="product-rating d-flex">
                            <div>
                                <rating-component rating={product.value?.rating?.rate}/>
                                <span class="total-reviews">({product.value?.rating?.count} reviews)</span>
                            </div>
                            <div class="ml-auto">
                                <v-btn onClick={addToCart} title="add to cart" density="default"
                                       icon="mdi-cart-plus"></v-btn>
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
