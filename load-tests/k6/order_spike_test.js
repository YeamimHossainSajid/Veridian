import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 500 },    // Warm up to 500 RPS
    { duration: '1m', target: 5000 },    // Ramp up to 5,000 RPS
    { duration: '2m', target: 20000 },   // Stress test at 20,000 RPS
    { duration: '30s', target: 0 },      // Ramp down
  ],
  thresholds: {
    http_req_duration: ['p(95)<5', 'p(99)<20'], // 95% under 5ms, 99% under 20ms
    http_req_failed: ['rate<0.001'],            // <0.1% errors
  },
};

const SYMBOLS = ['BTC-USD', 'ETH-USD', 'SOL-USD', 'AAPL', 'NVDA'];
const SIDES = ['BUY', 'SELL'];

export default function () {
  const symbol = SYMBOLS[Math.floor(Math.random() * SYMBOLS.length)];
  const side = SIDES[Math.floor(Math.random() * SIDES.length)];
  const price = (Math.random() * 500 + 100).toFixed(2);
  const quantity = (Math.random() * 2 + 0.1).toFixed(4);

  const payload = JSON.stringify({
    accountId: `ACC-${Math.floor(Math.random() * 1000)}`,
    symbol: symbol,
    side: side,
    orderType: 'LIMIT',
    price: price,
    quantity: quantity,
    timeInForce: 'GTC',
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer test-token',
    },
  };

  const res = http.post('http://localhost:8080/api/v1/orders', payload, params);

  check(res, {
    'status is 201': (r) => r.status === 201,
  });
}
