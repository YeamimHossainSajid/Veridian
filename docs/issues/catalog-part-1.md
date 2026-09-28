# Veridian Issue Catalog: Part 1 (Issues #1 – #100)

Welcome to Part 1 of the Veridian Issue Catalog. This section covers **Level 1 (Absolute Beginner / Good First Issue)** and **Level 2 (Beginner to Intermediate)** across all microservices and infrastructure components.

---

## Level 1: Absolute Beginner & Good First Issues (#1 – #50)

### Issue #1: Fix typo in root README elevator pitch
- **Service**: `documentation`
- **Labels**: `good-first-issue`, `documentation`, `beginner`
- **Description**: Correct grammatical typo in README overview section describing latency benchmarks.
- **Acceptance Criteria**: Verify markdown builds cleanly without broken links.

### Issue #2: Add .gitattributes rule for consistent line endings across OS
- **Service**: `infrastructure`
- **Labels**: `good-first-issue`, `tooling`, `beginner`
- **Description**: Configure `* text=auto eol=lf` in `.gitattributes` to prevent Windows CR/LF issues in git diffs.
- **Acceptance Criteria**: Git checkouts on Windows and Linux produce consistent LF characters.

### Issue #3: Add missing JavaDoc comments to OrderSide and OrderType enums
- **Service**: `common-lib`
- **Labels**: `good-first-issue`, `documentation`, `beginner`
- **Description**: Document all enum constants in `OrderSide.java` and `OrderType.java` with financial definitions.
- **Acceptance Criteria**: `./gradlew :common-lib:javadoc` completes without warnings.

### Issue #4: Add validation tests for OrderPlacedEvent serialization
- **Service**: `common-lib`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Add JUnit 5 test verifying Jackson serialization/deserialization of `OrderPlacedEvent`.
- **Acceptance Criteria**: All fields roundtrip cleanly with precision preserved for `BigDecimal`.

### Issue #5: Add missing @NonNull checks on CreateOrderRequest fields
- **Service**: `order-service`
- **Labels**: `good-first-issue`, `bug`, `beginner`
- **Description**: Add Bean Validation annotations (`@NotBlank`, `@NotNull`) on incoming order payloads.
- **Acceptance Criteria**: Sending null `price` on LIMIT orders returns HTTP 400 Bad Request.

### Issue #6: Add Docker healthcheck to api-gateway Dockerfile
- **Service**: `api-gateway`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Add `HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8080/actuator/health || exit 1` to Dockerfile.
- **Acceptance Criteria**: Docker inspect reports healthy status when gateway boots.

### Issue #7: Add Docker healthcheck to order-service Dockerfile
- **Service**: `order-service`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Implement curl-based container healthcheck targeting actuator endpoint.
- **Acceptance Criteria**: Container status reflects actuator state.

### Issue #8: Add Docker healthcheck to matching-engine Dockerfile
- **Service**: `matching-engine`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Add health probe instruction in `matching-engine/Dockerfile`.
- **Acceptance Criteria**: Docker daemon verifies service readiness.

### Issue #9: Add Docker healthcheck to risk-engine Dockerfile
- **Service**: `risk-engine`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Include container health checking script in image definition.
- **Acceptance Criteria**: Docker reports healthy after warmup.

### Issue #10: Add Docker healthcheck to market-data-service Dockerfile
- **Service**: `market-data-service`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Configure healthcheck probe in container manifest.
- **Acceptance Criteria**: Health probe functions in docker-compose.

### Issue #11: Add Docker healthcheck to payment-service Dockerfile
- **Service**: `payment-service`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Configure healthcheck probe in `payment-service/Dockerfile`.
- **Acceptance Criteria**: Verifiable container health state.

### Issue #12: Add Docker healthcheck to custody-service Dockerfile
- **Service**: `custody-service`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Configure healthcheck probe in `custody-service/Dockerfile`.
- **Acceptance Criteria**: Container status reports healthy.

### Issue #13: Add Docker healthcheck to settlement-service Dockerfile
- **Service**: `settlement-service`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Configure healthcheck probe in `settlement-service/Dockerfile`.
- **Acceptance Criteria**: Container status reports healthy.

### Issue #14: Add Docker healthcheck to analytics-service Dockerfile
- **Service**: `analytics-service`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Configure healthcheck probe in `analytics-service/Dockerfile`.
- **Acceptance Criteria**: Container status reports healthy.

### Issue #15: Format JSON payloads in docs/api/rest-api.md
- **Service**: `documentation`
- **Labels**: `good-first-issue`, `documentation`, `beginner`
- **Description**: Ensure all sample JSON requests and responses in documentation use 2-space indentation.
- **Acceptance Criteria**: Consistent JSON formatting across all API docs.

### Issue #16: Add missing error response schemas to docs/api/rest-api.md
- **Service**: `documentation`
- **Labels**: `good-first-issue`, `documentation`, `beginner`
- **Description**: Document standard RFC 7807 Problem Details error responses for HTTP 400, 401, 404, 500.
- **Acceptance Criteria**: Error code examples present for order rejection and account validation.

### Issue #17: Add .dockerignore to all microservice subdirectories
- **Service**: `infrastructure`
- **Labels**: `good-first-issue`, `docker`, `beginner`
- **Description**: Create `.dockerignore` ignoring `.git`, `build/`, `.gradle/`, and IDE files during docker builds.
- **Acceptance Criteria**: Docker build context size reduced significantly.

### Issue #18: Add logging statement on application startup in OrderServiceApplication
- **Service**: `order-service`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Add SLF4J log statement printing banner and active environment profiles on startup.
- **Acceptance Criteria**: Boot log clearly displays port and active profile.

### Issue #19: Add logging statement on application startup in MatchingEngineApplication
- **Service**: `matching-engine`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Add SLF4J startup logging indicating ring buffer size and pinned thread ID.
- **Acceptance Criteria**: Log output confirms Disruptor buffer allocation.

### Issue #20: Add logging statement on application startup in RiskEngineApplication
- **Service**: `risk-engine`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Log active risk thresholds and circuit breaker parameters on service init.
- **Acceptance Criteria**: Startup log displays max notional and exposure limits.

### Issue #21: Add unit test for BookOrder remaining quantity calculation
- **Service**: `matching-engine`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Verify `isFilled()` returns true when remaining quantity reaches zero or negative.
- **Acceptance Criteria**: Test passes for partial and complete fills.

### Issue #22: Add unit test for PriceLevel FIFO order removal
- **Service**: `matching-engine`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Test that orders added to `PriceLevel` poll in exact insertion order.
- **Acceptance Criteria**: Assertion verifies FIFO time-priority queue invariant.

### Issue #23: Add unit test for AccountRiskProfile position update
- **Service**: `risk-engine`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Test that `updatePosition(symbol, delta)` correctly aggregates long and short positions.
- **Acceptance Criteria**: Positions accurately reflect cumulative deltas.

### Issue #24: Add unit test for CircuitBreakerService halt and resume
- **Service**: `risk-engine`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Test `haltSymbol()` prevents trades and `resumeSymbol()` restores normal operation.
- **Acceptance Criteria**: Boolean flags match expected circuit state transitions.

### Issue #25: Add unit test for PaymentLedgerService deposit entry creation
- **Service**: `payment-service`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Test `recordDeposit()` saves a credit entry with generated transaction reference.
- **Acceptance Criteria**: Created entry contains positive balance and correct currency.

### Issue #26: Add unit test for CustodyService balance reservation
- **Service**: `custody-service`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Test `reserveBalance()` shifts amount from `freeBalance` to `lockedBalance`.
- **Acceptance Criteria**: Total wallet balance (`free + locked`) remains invariant.

### Issue #27: Add unit test for SettlementBatch status transitions
- **Service**: `settlement-service`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Test enum transition from PENDING to COMMITTED or FAILED.
- **Acceptance Criteria**: Invalid transitions throw IllegalStateException.

### Issue #28: Add unit test for TradeMetric calculation in VolumeAnalyticsService
- **Service**: `analytics-service`
- **Labels**: `good-first-issue`, `testing`, `beginner`
- **Description**: Test VWAP formula: `VWAP = sum(price * qty) / sum(qty)`.
- **Acceptance Criteria**: Assertion matches calculated VWAP with precision of 4 decimal places.

### Issue #29: Add .gitignore rule for local Gatling test reports
- **Service**: `infrastructure`
- **Labels**: `good-first-issue`, `tooling`, `beginner`
- **Description**: Add `build/reports/gatling/` and `*.log` to `.gitignore`.
- **Acceptance Criteria**: Load test run outputs are not tracked by git.

### Issue #30: Add .gitignore rule for k6 test summary artifacts
- **Service**: `infrastructure`
- **Labels**: `good-first-issue`, `tooling`, `beginner`
- **Description**: Ignore local test results generated by k6 (`*.json`, `*.html` reports in load-tests/).
- **Acceptance Criteria**: Clean working tree after local k6 run.

### Issue #31: Validate currency strings in PaymentLedgerService
- **Service**: `payment-service`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Ensure currency ISO 4217 code (USD, EUR, BTC, ETH) is checked before recording ledger entry.
- **Acceptance Criteria**: Non-supported currency strings throw `IllegalArgumentException`.

### Issue #32: Add custom exception for InsufficientBalanceException
- **Service**: `custody-service`
- **Labels**: `good-first-issue`, `refactor`, `beginner`
- **Description**: Replace generic `IllegalStateException` with specific domain exception.
- **Acceptance Criteria**: HTTP 422 Unprocessable Entity returned on insufficient balance.

### Issue #33: Add custom exception for SymbolHaltedException
- **Service**: `risk-engine`
- **Labels**: `good-first-issue`, `refactor`, `beginner`
- **Description**: Create explicit domain exception for orders submitted during market circuit breaker halts.
- **Acceptance Criteria**: Clear error response message with cooldown timer.

### Issue #34: Add custom exception for OrderNotFoundException
- **Service**: `order-service`
- **Labels**: `good-first-issue`, `refactor`, `beginner`
- **Description**: Create custom domain exception when order ID lookup fails in repository.
- **Acceptance Criteria**: Return HTTP 404 with structured JSON message.

### Issue #35: Add global @RestControllerAdvice in order-service
- **Service**: `order-service`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Implement global exception handler mapping domain exceptions to RFC 7807 responses.
- **Acceptance Criteria**: Standardized error responses across all order endpoints.

### Issue #36: Add global @RestControllerAdvice in risk-engine
- **Service**: `risk-engine`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Implement exception advice for risk limit and circuit breaker errors.
- **Acceptance Criteria**: Clean JSON error responses returned to callers.

### Issue #37: Add global @RestControllerAdvice in payment-service
- **Service**: `payment-service`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Add exception handler for invalid amounts or currency mismatches.
- **Acceptance Criteria**: Unified error payloads for ledger operations.

### Issue #38: Add global @RestControllerAdvice in custody-service
- **Service**: `custody-service`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Add exception handler for wallet reservation failures.
- **Acceptance Criteria**: Proper HTTP status codes for locking failures.

### Issue #39: Add global @RestControllerAdvice in settlement-service
- **Service**: `settlement-service`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Exception advice for clearing and saga transaction aborts.
- **Acceptance Criteria**: Structured JSON error formatting.

### Issue #40: Add global @RestControllerAdvice in analytics-service
- **Service**: `analytics-service`
- **Labels**: `good-first-issue`, `enhancement`, `beginner`
- **Description**: Implement controller advice for symbol not found or empty metric queries.
- **Acceptance Criteria**: Structured error responses.

### Issue #41: Add CORS configuration bean in api-gateway
- **Service**: `api-gateway`
- **Labels**: `good-first-issue`, `security`, `beginner`
- **Description**: Allow trusted web frontend origins for REST and WebSocket connections.
- **Acceptance Criteria**: Browser pre-flight OPTIONS requests return valid CORS headers.

### Issue #42: Add Actuator info metadata in order-service application.yml
- **Service**: `order-service`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose build version, git commit hash, and Java version in `/actuator/info`.
- **Acceptance Criteria**: `GET /actuator/info` displays application metadata.

### Issue #43: Add Actuator info metadata in matching-engine application.yml
- **Service**: `matching-engine`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose engine version and supported trading pairs in `/actuator/info`.
- **Acceptance Criteria**: Actuator info reflects configured trading symbols.

### Issue #44: Add Actuator info metadata in risk-engine application.yml
- **Service**: `risk-engine`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose risk parameters and circuit breaker configuration in `/actuator/info`.
- **Acceptance Criteria**: Risk properties visible in info endpoint.

### Issue #45: Add Actuator info metadata in market-data application.yml
- **Service**: `market-data-service`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose broadcaster frequency and simulated symbols in `/actuator/info`.
- **Acceptance Criteria**: Broadcast parameters visible via HTTP GET.

### Issue #46: Add Actuator info metadata in payment-service application.yml
- **Service**: `payment-service`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose supported currency list in Actuator info endpoint.
- **Acceptance Criteria**: Information displayed in actuator response.

### Issue #47: Add Actuator info metadata in custody-service application.yml
- **Service**: `custody-service`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose vault configurations and asset symbols in `/actuator/info`.
- **Acceptance Criteria**: Actuator endpoint output reflects settings.

### Issue #48: Add Actuator info metadata in settlement-service application.yml
- **Service**: `settlement-service`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose settlement cycle type (T+0) in `/actuator/info`.
- **Acceptance Criteria**: Settle parameters visible via HTTP.

### Issue #49: Add Actuator info metadata in analytics-service application.yml
- **Service**: `analytics-service`
- **Labels**: `good-first-issue`, `configuration`, `beginner`
- **Description**: Expose window aggregation size and ClickHouse host in info endpoint.
- **Acceptance Criteria**: Metadata returns valid JSON.

### Issue #50: Add badge for build status in root README.md
- **Service**: `documentation`
- **Labels**: `good-first-issue`, `documentation`, `beginner`
- **Description**: Add GitHub Actions CI build badge linking to main workflow run.
- **Acceptance Criteria**: Badge renders at top of README.md.

---

## Level 2: Beginner to Intermediate Issues (#51 – #100)

### Issue #51: Implement OpenAPI / Swagger UI in order-service
- **Service**: `order-service`
- **Labels**: `enhancement`, `documentation`, `level-2`
- **Description**: Add `springdoc-openapi-starter-webmvc-ui` dependency and configure Swagger UI at `/swagger-ui.html`.
- **Acceptance Criteria**: Interactive API documentation renders with models and endpoints.

### Issue #52: Implement OpenAPI / Swagger UI in risk-engine
- **Service**: `risk-engine`
- **Labels**: `enhancement`, `documentation`, `level-2`
- **Description**: Add Swagger OpenAPI documentation annotations for risk evaluation endpoints.
- **Acceptance Criteria**: Risk evaluation DTOs and endpoints documented in Swagger.

### Issue #53: Implement OpenAPI / Swagger UI in payment-service
- **Service**: `payment-service`
- **Labels**: `enhancement`, `documentation`, `level-2`
- **Description**: Add Springdoc OpenAPI integration for payment deposit/withdrawal endpoints.
- **Acceptance Criteria**: Swagger documentation accessible on port 8085.

### Issue #54: Implement OpenAPI / Swagger UI in custody-service
- **Service**: `custody-service`
- **Labels**: `enhancement`, `documentation`, `level-2`
- **Description**: Configure Swagger documentation for wallet reservation and release.
- **Acceptance Criteria**: Swagger documentation accessible on port 8086.

### Issue #55: Implement OpenAPI / Swagger UI in settlement-service
- **Service**: `settlement-service`
- **Labels**: `enhancement`, `documentation`, `level-2`
- **Description**: Configure Swagger documentation for clearing batches.
- **Acceptance Criteria**: Swagger documentation accessible on port 8087.

### Issue #56: Implement OpenAPI / Swagger UI in analytics-service
- **Service**: `analytics-service`
- **Labels**: `enhancement`, `documentation`, `level-2`
- **Description**: Configure Swagger documentation for VWAP metrics endpoints.
- **Acceptance Criteria**: Swagger documentation accessible on port 8088.

### Issue #57: Add pagination support to order history endpoint
- **Service**: `order-service`
- **Labels**: `enhancement`, `api`, `level-2`
- **Description**: Update `getOrdersByAccount` to accept Spring `Pageable` parameters (page, size, sort).
- **Acceptance Criteria**: Query returns paged results with total count and next page links.

### Issue #58: Add pagination support to payment ledger endpoint
- **Service**: `payment-service`
- **Labels**: `enhancement`, `api`, `level-2`
- **Description**: Add pageable query to `getLedger(accountId)` to avoid unbounded DB queries.
- **Acceptance Criteria**: Clients can paginate ledger history by 50 entries per page.

### Issue #59: Add pagination support to settlement list endpoint
- **Service**: `settlement-service`
- **Labels**: `enhancement`, `api`, `level-2`
- **Description**: Paginate `listSettlements` endpoint by batch creation timestamp descending.
- **Acceptance Criteria**: Efficient query execution with limit/offset.

### Issue #60: Implement order cancellation endpoint in OrderController
- **Service**: `order-service`
- **Labels**: `enhancement`, `core`, `level-2`
- **Description**: Add `DELETE /api/v1/orders/{orderId}` endpoint transitioning order to CANCELLED.
- **Acceptance Criteria**: Generates `OrderCancelledEvent` into transactional outbox.

### Issue #61: Add order status query by client order ID (ClOrdId)
- **Service**: `order-service`
- **Labels**: `enhancement`, `api`, `level-2`
- **Description**: Allow institutional clients to retrieve order state using their idempotency reference.
- **Acceptance Criteria**: Database index on `client_order_id` ensures O(1) lookup.

### Issue #62: Implement Stop-Loss order type validation
- **Service**: `order-service`
- **Labels**: `enhancement`, `trading`, `level-2`
- **Description**: Validate that STOP_LOSS orders specify a non-null trigger price (`stopPrice`).
- **Acceptance Criteria**: Reject STOP_LOSS request if `stopPrice` is absent.

### Issue #63: Implement Stop-Limit order type validation
- **Service**: `order-service`
- **Labels**: `enhancement`, `trading`, `level-2`
- **Description**: Validate that STOP_LIMIT orders specify both limit `price` and trigger `stopPrice`.
- **Acceptance Criteria**: HTTP 400 returned if either price parameter is missing.

### Issue #64: Implement Immediate-Or-Cancel (IOC) order handling in OrderBook
- **Service**: `matching-engine`
- **Labels**: `enhancement`, `matching`, `level-2`
- **Description**: Ensure unfilled residual quantity of IOC orders is cancelled immediately without resting on book.
- **Acceptance Criteria**: Remaining quantity discarded after crossing opposite side of book.

### Issue #65: Implement Fill-Or-Kill (FOK) order handling in OrderBook
- **Service**: `matching-engine`
- **Labels**: `enhancement`, `matching`, `level-2`
- **Description**: Verify sufficient book liquidity exists to fill entire quantity before executing; otherwise abort.
- **Acceptance Criteria**: FOK orders either fill 100% or zero with no partial fills.

### Issue #66: Add Micrometer custom counter for placed orders
- **Service**: `order-service`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Register `MeterRegistry` counter `orders_placed_total` tagged by symbol and side.
- **Acceptance Criteria**: Metric visible in `/actuator/prometheus`.

### Issue #67: Add Micrometer custom counter for rejected orders
- **Service**: `order-service`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Register counter `orders_rejected_total` tagged by rejection reason.
- **Acceptance Criteria**: Counter increments when validation or risk checks fail.

### Issue #68: Add Micrometer timer for order matching execution duration
- **Service**: `matching-engine`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Track `matching_engine_execution_time_seconds` using Micrometer `Timer`.
- **Acceptance Criteria**: Latency distribution percentiles (p50, p95, p99) exported.

### Issue #69: Add Micrometer gauge for active price levels count
- **Service**: `matching-engine`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Expose gauge tracking total active bid/ask price levels per symbol.
- **Acceptance Criteria**: Metric updates dynamically as levels are created and cleared.

### Issue #70: Add Micrometer counter for circuit breaker trips
- **Service**: `risk-engine`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Increment `risk_circuit_breaker_trips_total` tagged by symbol when volatility breaches threshold.
- **Acceptance Criteria**: Visible in Prometheus metrics scrape.

### Issue #71: Add Micrometer gauge for account margin utilization
- **Service**: `risk-engine`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Expose ratio of used margin to total account equity.
- **Acceptance Criteria**: Margin ratio tracked per active account.

### Issue #72: Add Micrometer counter for WebSocket client connections
- **Service**: `market-data-service`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Increment on connect and decrement on disconnect for active subscriber count.
- **Acceptance Criteria**: Gauge `market_data_active_ws_connections` accurately reflects live sessions.

### Issue #73: Add Micrometer counter for deposit and withdrawal volumes
- **Service**: `payment-service`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Track cumulative cash deposits and withdrawals tagged by currency.
- **Acceptance Criteria**: Metrics exported to Prometheus.

### Issue #74: Add Micrometer counter for settled trade volume
- **Service**: `settlement-service`
- **Labels**: `observability`, `metrics`, `level-2`
- **Description**: Track aggregate gross settled trade value and fee revenue.
- **Acceptance Criteria**: Metrics exported to Prometheus.

### Issue #75: Add Flyway database migration support to order-service
- **Service**: `order-service`
- **Labels**: `database`, `migration`, `level-2`
- **Description**: Add `org.flywaydb:flyway-core` and configure `V1__init_orders_schema.sql`.
- **Acceptance Criteria**: Schema applied automatically on application startup.

### Issue #76: Add Flyway database migration support to payment-service
- **Service**: `payment-service`
- **Labels**: `database`, `migration`, `level-2`
- **Description**: Configure Flyway migration `V1__init_payment_ledger.sql`.
- **Acceptance Criteria**: Ledger table created with appropriate constraints.

### Issue #77: Add Flyway database migration support to custody-service
- **Service**: `custody-service`
- **Labels**: `database`, `migration`, `level-2`
- **Description**: Configure Flyway migration `V1__init_custody_wallets.sql`.
- **Acceptance Criteria**: Unique constraint on `(account_id, asset)` verified.

### Issue #78: Add Flyway database migration support to settlement-service
- **Service**: `settlement-service`
- **Labels**: `database`, `migration`, `level-2`
- **Description**: Configure Flyway migration `V1__init_settlement_batches.sql`.
- **Acceptance Criteria**: Schema initialized cleanly.

### Issue #79: Add Testcontainers integration test for OrderRepository with PostgreSQL
- **Service**: `order-service`
- **Labels**: `testing`, `integration`, `level-2`
- **Description**: Create JUnit 5 test using `PostgreSQLContainer` verifying CRUD operations on `OrderEntity`.
- **Acceptance Criteria**: Test spins up container, runs migrations, and asserts persistence.

### Issue #80: Add Testcontainers integration test for OutboxRepository with PostgreSQL
- **Service**: `order-service`
- **Labels**: `testing`, `integration`, `level-2`
- **Description**: Test outbox polling query `findByProcessedFalseOrderByCreatedAtAsc`.
- **Acceptance Criteria**: Unprocessed outbox records returned in chronological order.

### Issue #81: Add Testcontainers integration test for LedgerRepository with PostgreSQL
- **Service**: `payment-service`
- **Labels**: `testing`, `integration`, `level-2`
- **Description**: Integration test for `LedgerEntry` insertion and account balance querying.
- **Acceptance Criteria**: Clean assertions in PostgreSQL test container.

### Issue #82: Add Testcontainers integration test for WalletRepository with PostgreSQL
- **Service**: `custody-service`
- **Labels**: `testing`, `integration`, `level-2`
- **Description**: Integration test verifying unique constraint on duplicate wallet creations.
- **Acceptance Criteria**: Duplicate `(accountId, asset)` throws constraint violation.

### Issue #83: Add Testcontainers integration test for SettlementRepository with PostgreSQL
- **Service**: `settlement-service`
- **Labels**: `testing`, `integration`, `level-2`
- **Description**: Integration test verifying batch query by tradeId and status.
- **Acceptance Criteria**: Settlement record queried accurately.

### Issue #84: Add Testcontainers integration test for Redis in risk-engine
- **Service**: `risk-engine`
- **Labels**: `testing`, `integration`, `level-2`
- **Description**: Verify `GenericContainer("redis:7-alpine")` stores and invalidates account risk cache.
- **Acceptance Criteria**: Redis commands execute against live container.

### Issue #85: Add Testcontainers integration test for Redis in market-data-service
- **Service**: `market-data-service`
- **Labels**: `testing`, `integration`, `level-2`
- **Description**: Test L2 order book caching in Redis sorted sets.
- **Acceptance Criteria**: Book depth cached and retrieved via Redis template.

### Issue #86: Add Embedded Kafka test for OrderService outbox publisher
- **Service**: `order-service`
- **Labels**: `testing`, `kafka`, `level-2`
- **Description**: Use `@EmbeddedKafka` to verify outbox poller publishes `order-events` topic correctly.
- **Acceptance Criteria**: Message received by test consumer matches outbox payload.

### Issue #87: Add Embedded Kafka test for MarketDataBroadcaster
- **Service**: `market-data-service`
- **Labels**: `testing`, `kafka`, `level-2`
- **Description**: Verify scheduled broadcaster produces messages to `market-data` topic.
- **Acceptance Criteria**: Consumer validates message headers and JSON depth payload.

### Issue #88: Add rate limiting filter to api-gateway with Redis token bucket
- **Service**: `api-gateway`
- **Labels**: `security`, `gateway`, `level-2`
- **Description**: Configure Spring Cloud Gateway `RequestRateLimiter` with `RedisRateLimiter(100, 200)`.
- **Acceptance Criteria**: Exceeding 200 requests/sec returns HTTP 429 Too Many Requests.

### Issue #89: Add IP whitelisting filter for FIX gateway connections
- **Service**: `api-gateway`
- **Labels**: `security`, `fix`, `level-2`
- **Description**: Restrict FIX port access to authorized institutional CIDR blocks.
- **Acceptance Criteria**: Unauthorized IP addresses rejected at connection handshake.

### Issue #90: Implement JWT expiration and signature validation in Gateway
- **Service**: `api-gateway`
- **Labels**: `security`, `jwt`, `level-2`
- **Description**: Verify RSA-256 JWT signatures and reject expired bearer tokens.
- **Acceptance Criteria**: Returns HTTP 401 Unauthorized with `WWW-Authenticate` header.

### Issue #91: Add correlation ID (X-Correlation-ID) propagation in Gateway
- **Service**: `api-gateway`
- **Labels**: `observability`, `tracing`, `level-2`
- **Description**: Generate UUID correlation header if missing and pass downstream to all microservices.
- **Acceptance Criteria**: Ingress requests without header receive new correlation ID in response.

### Issue #92: Propagate X-Correlation-ID in OrderService HTTP responses
- **Service**: `order-service`
- **Labels**: `observability`, `tracing`, `level-2`
- **Description**: Inject `X-Correlation-ID` from incoming request into response headers and MDC logging.
- **Acceptance Criteria**: Log entries include correlation ID for tracing.

### Issue #93: Propagate X-Correlation-ID in RiskEngine HTTP responses
- **Service**: `risk-engine`
- **Labels**: `observability`, `tracing`, `level-2`
- **Description**: Add servlet filter to extract correlation header into MDC context.
- **Acceptance Criteria**: Correlation ID present in application logs.

### Issue #94: Add request timing filter in api-gateway
- **Service**: `api-gateway`
- **Labels**: `observability`, `performance`, `level-2`
- **Description**: Measure roundtrip response latency for each routed service and log warning if $>100$ms.
- **Acceptance Criteria**: Response header `X-Response-Time-Millis` included.

### Issue #95: Add Dead Letter Queue (DLQ) topic definition for Kafka
- **Service**: `infrastructure`
- **Labels**: `kafka`, `reliability`, `level-2`
- **Description**: Configure `veridian-dlq` topic with 7-day retention for unprocessable poison-pill messages.
- **Acceptance Criteria**: Deserialization errors automatically route to DLQ topic.

### Issue #96: Implement Kafka SeekToCurrentErrorHandler with exponential backoff
- **Service**: `order-service`
- **Labels**: `kafka`, `reliability`, `level-2`
- **Description**: Configure Spring Kafka `DefaultErrorHandler` with 3 retries and backoff before routing to DLQ.
- **Acceptance Criteria**: Transient network errors retried without crashing container.

### Issue #97: Implement Kafka Consumer error handler in risk-engine
- **Service**: `risk-engine`
- **Labels**: `kafka`, `reliability`, `level-2`
- **Description**: Catch corrupt order messages and log alert to `risk-alerts` topic.
- **Acceptance Criteria**: Risk consumer remains operational after encountering bad payload.

### Issue #98: Implement Kafka Consumer error handler in matching-engine
- **Service**: `matching-engine`
- **Labels**: `kafka`, `reliability`, `level-2`
- **Description**: Isolate corrupt order payloads to prevent RingBuffer stall.
- **Acceptance Criteria**: Matching loop skips invalid payload and processes next event.

### Issue #99: Add custom health indicator for Kafka connectivity in order-service
- **Service**: `order-service`
- **Labels**: `observability`, `health`, `level-2`
- **Description**: Implement Spring Boot `HealthIndicator` checking Kafka broker metadata availability.
- **Acceptance Criteria**: Actuator health endpoint reports DOWN if Kafka is unreachable.

### Issue #100: Add custom health indicator for Redis connectivity in risk-engine
- **Service**: `risk-engine`
- **Labels**: `observability`, `health`, `level-2`
- **Description**: Implement `HealthIndicator` verifying Redis PING/PONG response.
- **Acceptance Criteria**: Actuator health status degrades to DOWN if Redis times out.
