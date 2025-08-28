<h1>Bosch E-commerce (Mini Backend)</h1>

<b>Stack:</b> Java 21, Spring Boot 3, PostgreSQL, Docker
<br/><br/>

<hr/>

<h2>API Docs</h2>

Swagger UI: <code>http://localhost:8080/swagger-ui/index.html</code>
<br/><br/>
OpenAPI JSON: <code>http://localhost:8080/v3/api-docs</code>
<br/><br/>

<hr/>

<h2>Setup</h2>

<b>Docker (compose):</b>
<br/>
<pre><code>docker compose up --build</code></pre>
<br/>

<hr/>

<h2>Quick Examples (More)</h2>

<!-- AUTH -->
<b>Auth:</b>
<br/>
<pre><code>POST /api/auth/register
Content-Type: application/json

{
  "username": "maja",
  "password": "pass123",
  "fullName": "Maja M"
}
</code></pre>

<pre><code>POST /api/auth/login
Content-Type: application/json

{
  "username": "maja",
  "password": "pass123"
}
</code></pre>
<br/>

<!-- PRODUCTS -->
<b>Get products (paginated + filters) — No auth required:</b>
<br/>
<pre><code>GET /api/products?page=0&amp;size=12&amp;sortBy=price&amp;direction=desc&amp;search=bosch&amp;priceMin=10&amp;priceMax=200
</code></pre>

<b>Get product by ID — No auth required:</b>
<br/>
<pre><code>GET /api/products/1
</code></pre>
<br/>

<!-- CART -->
<b>Get my cart:</b>
<br/>
<pre><code>GET /api/cart
Authorization: Bearer
</code></pre>

<b>Add to cart:</b>
<br/>
<pre><code>POST /api/cart/add
Authorization: Bearer
Content-Type: application/json

{
  "productId": 1
}
</code></pre>

<b>Update cart item quantity:</b>
<br/>
<pre><code>PUT /api/cart/item/5
Authorization: Bearer 
Content-Type: application/json

{
  "quantity": 3
}
</code></pre>

<b>Delete cart item:</b>
<br/>
<pre><code>DELETE /api/cart/item/5
Authorization: Bearer
</code></pre>

<br/>
<i>Notes:</i><br/>
– For protected routes include <code>Authorization: Bearer </code>.<br/>
– Pagination defaults: <code>page=0</code>, <code>size=10</code>. Allowed <code>direction</code>: <code>asc</code> | <code>desc</code>.
<br/>

<hr/>

<h2>Architecture</h2>

- JWT filter (<i>OncePerRequestFilter</i>) sets <i>Authentication</i> from token
  <br/><br/>
- Role-Based Access Control via <code>SecurityFilterChain</code> (permitAll / hasRole)
  <br/><br/>
- Cart isolation by <b>userId</b> from JWT; repositories always filter by user/cart
  <br/><br/>
- Input validation (Bean Validation); whitelisted sort fields
  <br/><br/>
- Global error handler (<code>@RestControllerAdvice</code>) returns uniform JSON
  <br/><br/>
- CORS enabled for frontends
  <br/><br/>

<hr/>

<h2>Self-Assessment</h2>

<b>Challenges:</b> robust JWT parsing &amp; skipping public routes; strict user-scoped cart; clean validation
<br/><br/>
<b>Solutions:</b> dedicated JWT filter with strong <code>Authorization</code> guard; repo methods scoped by userId; centralized exception handling.
<br/><br/>
<b>With more time:</b> unit/integration tests (Testcontainers), Flyway migrations, refresh tokens, richer Swagger and rate-limiting.
<br/><br/>

<hr/>


<h2>Environment Notes</h2>

<b>Local run (IntelliJ):</b>  
– All endpoints and JWT authentication work as expected.  
<br/><br/>

<b>Docker (docker compose up --build):</b>  
– Application builds and starts correctly, but authentication behaves inconsistently.  
– Even some public routes may return <code>401 Unauthorized</code> due to differences in JWT filter/env configuration.  
– This issue does not appear in local (non-Docker) environment.  
<br/>
<hr>
<h2>Tests Status</h2>

<b>Note:</b> Unit tests are not implemented yet. Focus was on stable core endpoints and JWT.
<br/><br/>

<hr/>
