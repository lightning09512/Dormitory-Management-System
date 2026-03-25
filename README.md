# Hệ Thống Quản Lý Ký Túc Xá (KTX Manager)

## 📋 Giới Thiệu

Đây là ứng dụng desktop được phát triển bằng Java Swing và Hibernate ORM để quản lý ký túc xá, với kiến trúc 3-tier và các design patterns hiện đại.

## 🏗️ Kiến Trúc

```
┌─────────────────────────────────────────────┐
│                UI Layer                 │
│  ┌─────────────┬─────────────┐      │
│  │MainFrame     │ Dialogs     │      │
│  └─────────────┴─────────────┘      │
│         │                              │
│   Controller Layer                   │
│  ┌─────────────┬─────────────┐      │
│  │Controllers   │AuthCtrl     │      │
│  └─────────────┴─────────────┘      │
│         │                              │
│    Service Layer                    │
│  ┌─────────────┬─────────────┐      │
│  │Services     │AuthService   │      │
│  └─────────────┴─────────────┘      │
│         │                              │
│  Repository Layer                  │
│  ┌─────────────┬─────────────┐      │
│  │Repositories │ JPA/Hibernate │      │
│  └─────────────┴─────────────┘      │
│         │                              │
│     Database Layer                  │
│  ┌─────────────────────────────────┐      │
│  │      MySQL Database           │      │
│  └─────────────────────────────────┘      │
└─────────────────────────────────────────────┘
```

## ✨ Tính Năng

### 🔐 Authentication
- Đăng nhập với BCrypt password hashing
- Quản lý vai trò (Staff/Manager)
- Session management

### 👥 Student Management
- Thêm/Sửa/Xóa sinh viên
- Tìm kiếm thông minh (MSSV + Tên)
- Validation đầu vào

### 🏠 Room Management  
- Quản lý phòng và dãy nhà
- Lọc theo trạng thái
- Cập nhật sức chứa

### 📄 Contract Management
- Lập hợp đồng sinh viên-phòng
- Quản lý hợp đồng hiệu lực
- Tính toán giá thuê

### 📊 Dashboard & Statistics
- Thống kê tổng quan
- Số liệu real-time
- Visual reports

## 🔧 Công Nghệ

### Backend
- **Java 17** - Modern Java features
- **Hibernate 6.4.4** - Latest ORM
- **MySQL 8.3** - Reliable database
- **Maven** - Dependency management

### Frontend
- **Java Swing** - Core desktop framework
- **FlatLaf (Mac Light)** - Modern, high-performance look & feel
- **FlatLaf Extras** - SVG icon support for crisp visuals
- **MigLayout** - Flexible and responsive UI layouts
- **Custom UITheme** - Centralized design system with consistent scales

### Quality Assurance
- **JUnit 5** - Unit testing
- **Mockito** - Mock testing
- **Bean Validation** - Input validation
- **SLF4J + Logback** - Structured logging

## 🚀 Cài Đặt

### Yêu Cầu Hệ Thống
- **Java 17+**
- **MySQL 8.0+**
- **Maven 3.6+**

### Các Bước Cài Đặt

1. **Clone repository**
   ```bash
   git clone <repository-url>
   cd QuanLyKTX
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE QuanLyKTX CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Configuration**
   ```properties
   # Cập nhật application.properties
   database.username=your_username
   database.password=your_password
   database.url=jdbc:mysql://localhost:3306/QuanLyKTX
   ```

4. **Build & Run**
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.ktx.App"
   ```

## 📁 Cấu Trúc Project

```
QuanLyKTX/
├── src/
│   ├── main/
│   │   ├── java/com/ktx/
│   │   │   ├── model/          # JPA Entities
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── service/         # Business logic layer
│   │   │   ├── controller/      # Presentation layer
│   │   │   ├── view/           # Swing UI components
│   │   │   ├── config/         # Configuration classes
│   │   │   └── util/           # Utility classes
│   │   └── resources/
│   │       ├── META-INF/
│   │       │   └── persistence.xml  # JPA configuration
│   │       ├── application.properties    # App configuration
│   │       └── logback.xml           # Logging configuration
│   └── test/                     # Unit tests
└── pom.xml                      # Maven configuration
```

## 🧪 Testing

### Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=SinhVienServiceTest

# Run with coverage
mvn test jacoco:report
```

### Test Coverage
- Unit tests: Service layer
- Integration tests: Repository layer  
- UI tests: Form validation

## 📝 Logging

### Log Files
- **Console**: Real-time logging
- **File**: `logs/ktx-application.log`
- **Error**: `logs/ktx-error.log`

### Log Levels
- `DEBUG`: Detailed debugging
- `INFO`: General information
- `WARN`: Warning messages
- `ERROR`: Error conditions

## 🔒 Security

### Authentication
- BCrypt password hashing
- Session timeout: 30 minutes
- Role-based access control

### Input Validation
- Bean Validation annotations
- Custom validators
- SQL injection prevention

## 🎨 UI/UX Features

### Modern Design
- **FlatLaf Integration**: macOS-style interfaces with clean lines.
- **Responsive Toolbars**: Smart two-row layouts for management controls.
- **Card-based Dashboard**: Rounded corners and subtle accent styling.
- **SVG Graphics**: Resolution-independent icons for high-DPI displays.
- **Standardized Scaling**: 40px base height for all inputs and buttons.

### Accessibility
- Keyboard navigation
- Screen reader support
- High contrast mode

## 📈 Performance

### Database Optimization
- Connection pooling (HikariCP)
- Query optimization
- Lazy loading

### Caching Strategy
- Entity caching
- Query result caching
- Session caching

## 🔄 Deployment

### Production Configuration
```properties
# Environment variables
DB_USER=prod_user
DB_PASSWORD=prod_password
SHOW_SQL=false
FORMAT_SQL=false
```

### Health Checks
- Database connectivity
- Memory usage
- Active sessions

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection**
   ```properties
   # Kiểm tra MySQL service
   # Verify credentials
   # Test connection string
   ```

2. **Hibernate Issues**
   ```properties
   # Enable SQL logging
   hibernate.show_sql=true
   hibernate.format_sql=true
   ```

3. **UI Problems**
   - Check FlatLaf installation
   - Verify Java version
   - Clear Maven cache

## 📞 Hỗ Trợ

### Documentation
- [API Documentation](docs/api/)
- [User Manual](docs/user-manual/)
- [Developer Guide](docs/developer-guide/)

### Contact
- Email: support@ktx-manager.com
- Issues: [GitHub Issues](link-to-repo/issues)

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit your changes
4. Push to the branch
5. Create Pull Request

---

**Version**: 1.1.0 (UI Modernized)  
**Last Updated**: 2026  
**Maintainer**: KTX Manager Team
