<script lang="tsx">
import {defineComponent, ref} from 'vue';
export default defineComponent({
	name: 'FooterComponent',
	props: {
		showFooter: {
			type: Boolean,
			default: true,
		},
	},
	setup(props) {
		const isShow = ref(props.showFooter);
		const target = ref('_blank');
		return () => (
			<div>
				{isShow.value && (
					<footer>
						<div class="container">
							<div class="row">
								<div class="col-md-12">
									<div class="inner-content">
										<p>Copyright &copy; 2024 Man Clothes</p>
									</div>
								</div>
							</div>
						</div>
					</footer>
				)}
			</div>
		);
	},
});
</script>

<style scoped lang="scss">
@import 'style.scss';

</style>
