# Changelog

## [1.9.3]

- Expose encodedExecutionId on the Execution that can be used to correlate with broker FIX's secondary execution id.

## [1.9.2]

- Expose cash credit from account state events in Java API Client

## [1.9.1]

- Fix the issue where Net Open Position values could sometimes be applied to other wallets 

## [1.9.0]

- Report Net Margin Open Positions in Account State Events
- Improve connection User-Agent info

## [1.8.4] 

- Improve javadoc annotations
- Ensure Java API detects end of event stream
- Escape special XML characters in string values, such as usernames, passwords and instruction IDs
- Add the ability to specify connection and read timeouts when using the Lmax API
- Protect against XXE attacks
- Fix name of CLOSE_OUT_ORDER OrderType
- Add support for stopLossInstructionId

## [1.8.3]

- Enable use of stopProfitInstructionId
- Call session disconnects listener when the heartbeat request receives a 403
