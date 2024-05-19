<script lang="tsx">
import { defineComponent, ref } from "vue";
export default defineComponent({
	name : 'LoadingComponent',
	props : {
		loading : {
			type : Boolean,
			default : false
		},
		timeOut : {
			type : Number,
			default : 1
		}
	},
	setup(props){
		const loadingProps = ref(props.loading);
		const timeOut = ref(props.timeOut);
		setTimeout(() => {
			loadingProps.value = false;
		}, timeOut.value * 1000);
		return () => (
			<div>
				{loadingProps.value && (
					<div class="loading">
						<div class="spinner-border spinner-center" role="status">
							<span class="sr-only">...</span>
						</div>
					</div>
				)}
			</div>
		)
	}
})
</script>

<style scoped lang="css">
@import "style.css";
</style>