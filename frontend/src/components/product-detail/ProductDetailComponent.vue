<script lang="tsx">
import {defineComponent, reactive} from "vue";

export default defineComponent({
    name: 'ProductDetailComponent',
    props: {
        product: {
            type: Object,
            default: () => {
                return {
                    name: '',
                    price: 0,
                    description: '',
                    rating: 0,
                    totalReviews: 0,
                    imageThumbnail: ''
                }
            },
            required: true
        }
    },
    setup(props) {
        const product = reactive(props.product);
        let activeClass = 0;
        const currentThumbnail = (image: any, index: any) => {
            product.imageThumbnail = image;
            activeClass = index;
        }
        const addToCart = () => {
            product.cartCount += 1;
        }
        return (
            <div class="row">
                <div class="col-md-6">
                    <img class="img-fluid" src={product?.imageThumbnail} alt=""/>
                    <div class="product-thumbnails">
                        <ul>
                            {product?.imageList.map((image: any, index: any) => (
                                <li
                                    class={activeClass === index ? 'thumbnail-active' : ''}
                                    key={index}
                                >
                                    <img
                                        onClick={() => currentThumbnail(image.imageThumbnail, index)}
                                        src={image.imageThumbnail}
                                        alt=""
                                    />
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
                <div class="col-md-4">
                    <h1 class="my-4">{`${product?.title} - ${product?.price}`}</h1>
                    <h3 class="my-3">
                        <li class="fa fa-shopping-cart"></li>
                        {product?.cartCount}
                    </h3>
                    {product?.stockAvailability ? (
                        <h4>Stock Available</h4>
                    ) : (
                        <h4>Oops!! Out Of Stock</h4>
                    )}
                    <h3 class="my-3">Details</h3>
                    <ul>
                        {product?.infoList.map((detail: any, index: any) => (
                            <li key={index}>{detail}</li>
                        ))}
                    </ul>
                    <button
                        type="button"
                        class="btn btn-outline-primary btn-lg btn-block btn-custom-color"
                        onClick={addToCart}
                    >
                        ADD TO CART
                    </button>
                </div>
            </div>
        );
    }
})
</script>
<style lang="scss" scoped>
@import "style";
</style>