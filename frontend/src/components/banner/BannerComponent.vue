<script lang="tsx">
import {defineComponent, ref} from "vue";

export default defineComponent({
	name: 'BannerComponent',
	props: {
		showBanner: {
			type: Boolean,
			default: true,
		},
	},
	setup(props) {
		const isShow = ref(props.showBanner);
		const carousel = ref([
			'src/assets/images/header/slide_01.jpg',
			'src/assets/images/header/slide_02.jpg',
			'src/assets/images/header/slide_03.jpg',
		]);
		return () => (
			<div>
				{isShow.value && (
					<v-carousel>
						{carousel.value?.map((item,index) => (
							<v-carousel-item key={index}>
                                <v-img
                                aspect-ratio="16/9"
                                cover={true}
                                src={item}
                                ></v-img>
							</v-carousel-item>
						))}
					</v-carousel>
				)}
			</div>
		)
	}
});

</script>