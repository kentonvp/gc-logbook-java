.PHONY: build-backend server logs shutdown

build-backend:
	pushd backend && \
	./gradlew spotlessApply && \
	./gradlew build && \
	popd

serve:
	docker compose down && \
	docker compose up -d --build

logs:
	docker compose logs -f

shutdown:
	docker compose down
